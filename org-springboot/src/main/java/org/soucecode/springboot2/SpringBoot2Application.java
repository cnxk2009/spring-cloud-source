package org.soucecode.springboot2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class SpringBoot2Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot2Application.class, args);
    }

    @Override protected SpringApplicationBuilder configure(SpringApplicationBuilder application) { return application.sources(SpringBoot2Application.class); }

    /**
     * commandLineRunner方法被注解成了@Bean，程序在启动的时候，这个方法会被自动执行，
     * 将整个Application Context里面所有的bean都打印出来，这个方法是便于各位了解SpringBoot底层原理的，
     * 在生产环境中可以删除
     * @param ctx
     * @return
     */
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
}
