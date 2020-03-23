package com.jacken.nginxdemo.service.impl;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.jacken.nginxdemo.service.PushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangqiang
 * @version 1.0
 * @date 2020/3/14 9:47
 */

@Service
@Slf4j
public class PushServiceImpl implements PushService {

    @Override
    public void push() {

    }

    //极光推送>>Android
    //Map<String, String> parm是我自己传过来的参数,同学们可以自定义参数
    public static void jpushAndroid(Map<String, String> parm) {

        String appkey="68a1592b63fd2f3a0873bf41";
        String masterKey="7b8e4410a345fd0777f57bb1";

        //创建JPushClient(极光推送的实例)
        JPushClient jpushClient = new JPushClient(masterKey, appkey);
        //推送的关键,构造一个payload
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())//指定android平台的用户
                .setAudience(Audience.all())//你项目中的所有用户
                //.setAudience(Audience.registrationId(parm.get("id")))//registrationId指定用户
                .setNotification(Notification.android(parm.get("msg"), "脸咔", parm))
                //发送内容
                .setOptions(Options.newBuilder().setApnsProduction(false).build())
                //这里是指定开发环境,不用设置也没关系
                .setMessage(Message.content(parm.get("msg")))//自定义信息
                .build();

        try {
            PushResult pu = jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        //设置推送参数
        //这里同学们就可以自定义推送参数了
        Map<String, String> parm = new HashMap<String, String>();
        //这里的id是,移动端集成极光并登陆后,极光用户的rid
        //parm.put("id", "1104a897923c458b286");
        //设置提示信息,内容是文章标题
        parm.put("msg","御龙镜中影 评论了你1111");
       jpushAndroid(parm);
    }


    public static PushPayload buildPushObject_android_and_ios(Long userId, String alert) {
        boolean tag=false;
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(String.valueOf(userId)))
                .setNotification(Notification.newBuilder().setAlert(alert)
                        .addPlatformNotification(
                                AndroidNotification.newBuilder().setTitle("脸咔").build())
                        .addPlatformNotification(
                                IosNotification.newBuilder().incrBadge(0).build())
                        .build())
                .setOptions(Options.newBuilder().setApnsProduction(tag).build())
                .build();
    }

//    public static void main(String[] args) {
//        //正式环境
//        String appkey="9aac8c5443a87708905bc4e7";
//        String masterKey="41ce0e3a6ed64a56fe5b4728";
//        ClientConfig clientConfig = ClientConfig.getInstance();
//
//        final JPushClient jpushClient = new JPushClient(masterKey, appkey, null, clientConfig);
//        final PushPayload payload = buildPushObject_android_and_ios(430447610340581376L, "你的好友王强 评论了你");
//        try {
//            PushResult result = jpushClient.sendPush(payload);
//            log.info("Got result - " + result);
//        } catch (Exception e) {
//            log.error("推送失败", e.getMessage());
//        }
//    }
}
