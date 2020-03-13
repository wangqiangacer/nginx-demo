package com.jacken.nginxdemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangqiang
 * @version 1.0
 * @date 2020/3/11 10:48
 */
@RestController
public class IndexController {

    @Value("${server.port}")
    public  int port;

    @RequestMapping("/index")
    public String index(){
        System.out.println("这是"+port);
        return "这是"+port;
    }
}
