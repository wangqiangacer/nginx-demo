package com.jacken.nginxdemo.utils;

import io.rong.RongCloud;
import io.rong.methods.user.User;
import io.rong.methods.user.blacklist.Blacklist;
import io.rong.methods.user.onlinestatus.OnlineStatus;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;

/**
 * @author wangqiang
 * @version 1.0
 * @date 2020/3/14 20:40
 */
public class RongYunUtils {

    public static void main(String[] args)  throws Exception{
        System.out.println(getRongCloudToken());
    }


    /**
     * 获取融云的token
     * @return
     */
    public static   TokenResult  getRongCloudToken() throws Exception{
        String appKey = "8w7jv4qb83kty";
        String appSecret = "4otCHdsfe18jrv";

        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
        User user = rongCloud.user;


        /**
         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/user/user.html#register
         *
         * 注册用户，生成用户在融云的唯一身份标识 Token
         */
        UserModel userModel = new UserModel()
                .setId("430447610340581376")
                .setName("御龙镜中隐")
                .setPortrait("https://img.imlianka.com/faceImg/73838dbc-1534-42e8-80e8-a03664535bdf.png");
        TokenResult result = user.register(userModel);
        System.out.println("用户昵称："+user.get(userModel).getUserName());
        OnlineStatus status = new OnlineStatus(appKey, appSecret, rongCloud);
        //查看用户是否在线1: 在线 0: 离线
        System.out.println("用户在线状态："+status.check(userModel).getStatus());
        //添加黑名单
        Blacklist blacklist = new Blacklist(appKey, appSecret, rongCloud);
        UserModel userBlack = new UserModel()
                .setId("363807774372798464")
                .setName("康良")
                .setPortrait("https://img.imlianka.com/avatar/B49F23A40FD4143D989B84591DAD7999.png");
        System.out.println("添加黑名单状态码："+blacklist.add(userBlack).getCode());
        //查询该用户的黑名单
        for (UserModel model : blacklist.getList(userModel).getUsers()) {
            System.out.println("该用户的给名单有："+model);
        }
        return  result;
    }



}
