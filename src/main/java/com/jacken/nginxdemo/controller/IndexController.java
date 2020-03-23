package com.jacken.nginxdemo.controller;

import com.jacken.nginxdemo.service.AuthService;
import com.jacken.nginxdemo.utils.FaceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    private AuthService  authService;

    @RequestMapping("/index")
    public String index(){
        System.out.println("这是"+port);
        return "你好世界";
    }

    @RequestMapping("/getToken")
    public  String getToken(){
        return  authService.getAuthToken();
    }

    @PostMapping("/getName")
    public String getName(String name){
        System.out.println(name);
        return "你好"+name;
    }
}
