package org.soucecode.springboot2.controller;

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
public class FeignController {

    @Autowired
    private UserApi userApi;

    @GetMapping(value = "/feignuser")
    public String feignUser(){
        return userApi.findUser();
    }

}