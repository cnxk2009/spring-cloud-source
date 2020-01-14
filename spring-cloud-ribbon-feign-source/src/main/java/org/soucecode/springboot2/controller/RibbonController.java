package org.soucecode.springboot2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author cnxk
 * @Date 2020/1/14
 * @Version V1.0.0
 **/
@RestController
public class RibbonController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/ribbon")
    public String ribbon(){
         return this.restTemplate.getForObject("http://SERVICE:8080/user",String.class);
    }

}
