package org.soucecode.springboot2.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.soucecode.springboot2.client.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cnxk
 * @Date 2020/1/14
 * @Version V1.0.0
 **/
@RestController
public class HystrixController {

    @Autowired
    private UserApi userApi;

    @HystrixCommand(fallbackMethod = "userFallback",
            groupKey = "userGroup",
            commandKey = "hystrixtest",
            threadPoolKey = "userThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize",value ="2"),
                    @HystrixProperty(name="maxQueueSize",value ="2"),
                    @HystrixProperty(name="queueSizeRejectionThreshold",value ="1"),
            }
    )
    @GetMapping("/hystrixuser")
    public String findUser(){
        return userApi.findUser();
    }

    public String userFallback(){
        return "userfallback";
    }
}