package com.jacken.nginxdemo;

import com.jacken.nginxdemo.config.CrosFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


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

    /**
     * 配置跨域访问的过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean registerFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.addUrlPatterns("/*");
        bean.setFilter(new CrosFilter());
        return bean;
    }

}
