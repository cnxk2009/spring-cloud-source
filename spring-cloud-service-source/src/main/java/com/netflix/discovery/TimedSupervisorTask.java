package com.netflix.discovery;

import java.util.TimerTask;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import com.netflix.servo.monitor.Counter;
import com.netflix.servo.monitor.LongGauge;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.monitor.Monitors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A supervisor task that schedules subtasks while enforce a timeout.
 * Wrapped subtasks must be thread safe.
 *
 * @author David Qiang Liu
 */
public class TimedSupervisorTask extends TimerTask {
    private static final Logger logger = LoggerFactory.getLogger(TimedSupervisorTask.class);

    private final Counter timeoutCounter;
    private final Counter rejectedCounter;
    private final Counter throwableCounter;
    private final LongGauge threadPoolLevelGauge;

    private final ScheduledExecutorService scheduler;
    private final ThreadPoolExecutor executor;
    private final long timeoutMillis;
    private final Runnable task;

    private final AtomicLong delay;
    private final long maxDelay;

    public TimedSupervisorTask(String name, ScheduledExecutorService scheduler, ThreadPoolExecutor executor,
                               int timeout, TimeUnit timeUnit, int expBackOffBound, Runnable task) {
        this.scheduler = scheduler;
        this.executor = executor;
        this.timeoutMillis = timeUnit.toMillis(timeout);
        this.task = task;
        this.delay = new AtomicLong(timeoutMillis);
        this.maxDelay = timeoutMillis * expBackOffBound;

        // Initialize the counters and register.
        timeoutCounter = Monitors.newCounter("timeouts");
        rejectedCounter = Monitors.newCounter("rejectedExecutions");
        throwableCounter = Monitors.newCounter("throwables");
        threadPoolLevelGauge = new LongGauge(MonitorConfig.builder("threadPoolUsed").build());
        Monitors.registerObject(name, this);
    }

    @Override
    public void run() {
        Future<?> future = null;
        try {
            //异步非阻塞
            future = executor.submit(task);
            threadPoolLevelGauge.set((long) executor.getActiveCount());
            //指定等待子线程的最长时间
            future.get(timeoutMillis, TimeUnit.MILLISECONDS);  // block until done or timeout
            //不超时的情况下设置初始值30,等待下次循环调用。
            delay.set(timeoutMillis);
            threadPoolLevelGauge.set((long) executor.getActiveCount());
        } catch (TimeoutException e) {
            logger.warn("task supervisor timed out", e);
            timeoutCounter.increment();

            long currentDelay = delay.get();
            //巧妙的设计: 当30秒超时时，设置30*2=60秒 与最大值之间取最小值，保证不超过最大值
            long newDelay = Math.min(maxDelay, currentDelay * 2);
            //cas并发编程 设置为最新的值，考虑到多线程，所以用了CAS
            delay.compareAndSet(currentDelay, newDelay);

        } catch (RejectedExecutionException e) {
            //一旦线程池的阻塞队列中放满了待处理任务，触发了拒绝策略，就会将调度器停掉
            if (executor.isShutdown() || scheduler.isShutdown()) {
                logger.warn("task supervisor shutting down, reject the task", e);
            } else {
                logger.warn("task supervisor rejected the task", e);
            }

            rejectedCounter.increment();
        } catch (Throwable e) {
            if (executor.isShutdown() || scheduler.isShutdown()) {
                logger.warn("task supervisor shutting down, can't accept the task");
            } else {
                logger.warn("task supervisor threw an exception", e);
            }

            throwableCounter.increment();
        } finally {
            //这里任务要么执行完毕，要么发生异常，都用cancel方法来清理任务；
            if (future != null) {
                future.cancel(true);
            }
            //只要调度器没有停止，就再指定等待时间之后在执行一次同样的任务
            if (!scheduler.isShutdown()) {
                //假设外部调用时传入的超时时间为30秒（构造方法的入参timeout），最大间隔时间为50秒(构造方法的入参expBac kOffBound)
                // 如果最近一次任务没有超时，那么就在30秒后开始新任务，
                // 如果最近一次任务超时了，那么就在50秒后开始新任务（异常处理中有个乘以二的操作，乘以二后的60秒超过了最大 间隔50秒）
                scheduler.schedule(this, delay.get(), TimeUnit.MILLISECONDS);
            }
        }
    }
}