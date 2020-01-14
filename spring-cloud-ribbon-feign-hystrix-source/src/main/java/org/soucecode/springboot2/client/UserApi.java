package org.soucecode.springboot2.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author cnxk
 * @Date 2020/1/14
 * @Version V1.0.0
 **/
@FeignClient(name="SERVICE")
public interface UserApi {

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String findUser();

}