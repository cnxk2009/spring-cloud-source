package org.soucecode.springboot2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cnxk
 * @Date 2020/1/14
 * @Version V1.0.0
 **/
@RestController
public class UserController {

    @GetMapping("/user")
    public String findUser(){
        return "query user cnxk!";
    }

}