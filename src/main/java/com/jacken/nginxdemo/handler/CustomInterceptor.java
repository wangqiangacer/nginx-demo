package com.jacken.nginxdemo.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangqiang
 * @version 1.0
 * @date 2020/3/21 20:16
 *
 * 请求链路的说明  先是监听器 再是过滤器 再是 拦截器 最后才是controller  Filter--->Servlet --->Interceptor --controller
 */
@Configuration
@Slf4j
public class CustomInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
       log.info("preHandle..........");
        return false;
    }
}
