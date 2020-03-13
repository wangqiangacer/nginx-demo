package com.jacken.nginxdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * nginx   服务器地址   /usr/local/nginx/sbin
 * http://www.wqjacken.com/index  修改本地hosts文件
 *resource 下面有具体配置 以及负载均衡
 */
@SpringBootApplication
public class NginxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NginxDemoApplication.class, args);
    }

}
