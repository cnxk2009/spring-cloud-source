package org.soucecode.springboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableZuulProxy
@SpringBootApplication
public class GateWayZuulApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(GateWayZuulApplication.class, args);
    }

    @Override protected SpringApplicationBuilder configure(SpringApplicationBuilder application) { return application.sources(GateWayZuulApplication.class); }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}