package com.jacken.nginxdemo.service;

/**
 * @author wangqiang
 * @version 1.0
 * @date 2020/3/14 11:30
 */
public interface AuthService {

    /**
     * 获取access_token
     * @return
     */
    String getAuthToken();

}
