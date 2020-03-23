package com.jacken.nginxdemo.utils;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangqiang
 * @version 1.0
 * @date 2020/3/14 13:05
 * 人脸识别(在线网络识别)人脸搜索
 */
@SuppressWarnings("ALL")
@Component
public class FaceUtils {

    public static void main(String[] args) throws Exception{
        //人脸识别可以识别图片数量 年龄
        System.out.println(faceDetect("C:\\Users\\wangqiang\\Desktop\\微信图片_20200314143452.jpg"));


        //人脸对比 人脸相似度得分，推荐阈值80分
        //System.out.println(faceMatch("https://img.imlianka.com/faceImg/73838dbc-1534-42e8-80e8-a03664535bdf.png", "C:\\Users\\wangqiang\\Desktop\\微信图片_20200314133045.jpg"));

        //人脸搜索

        //System.out.println(faceSearch("https://img.imlianka.com/faceImg/73838dbc-1534-42e8-80e8-a03664535bdf.png"));

    }


    /**
     * 人脸搜索
     * @return
     */
    public static String faceSearch(String imgUrl) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            byte[] data = getPageData(imgUrl);
            String image1 = Base64Util.encode(data);
            Map<String, Object> map = new HashMap<>();
            map.put("image", image1);
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", "LKFaceProduct");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");
            String param = GsonUtils.toJson(map);
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAuthToken();
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 人脸对比功能
     * @param imgUrl
     * @param imgUrl1
     * @return
     */
    public static String faceMatch(String imgUrl,String imgUrl1) {

        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {

            byte[] bytes1 = getPageData(imgUrl);

            byte[] bytes2 =getPageLocalData (imgUrl1);
            String image1 = Base64Util.encode(bytes1);
            String image2 = Base64Util.encode(bytes2);

            List<Map<String, Object>> images = new ArrayList<>();

            Map<String, Object> map1 = new HashMap<>();
            map1.put("image", image1);
            map1.put("image_type", "BASE64");
            map1.put("face_type", "LIVE");
            map1.put("quality_control", "LOW");
            map1.put("liveness_control", "NORMAL");

            Map<String, Object> map2 = new HashMap<>();
            map2.put("image", image2);
            map2.put("image_type", "BASE64");
            map2.put("face_type", "LIVE");
            map2.put("quality_control", "LOW");
            map2.put("liveness_control", "NORMAL");

            images.add(map1);
            images.add(map2);
            String param = GsonUtils.toJson(images);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAuthToken();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取百度token
     * @return
     */
    public  static String getAuthToken(){
        // 官网获取的 API Key 更新为你注册的
        String clientId = "3X0wYeOtAdI8hWfudVa5jiIx";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "fb2MLbWHGBavDfMqjCqDMd4NimIMlSct";
        String token = getAuth(clientId, clientSecret);
        return token;
    }

    /**
     * 人脸检测
     * @return
     */
    public static String faceDetect(String imgUrl)  throws Exception{
        // 官网获取的 API Key 更新为你注册的
        String clientId = "3X0wYeOtAdI8hWfudVa5jiIx";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "fb2MLbWHGBavDfMqjCqDMd4NimIMlSct";

        // 请求url
        byte[] data=null;
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        if (imgUrl.contains("http")){
             data = getPageData(imgUrl);
        }else {
            data=getPageLocalData(imgUrl);
        }

        String image1 = Base64Util.encode(data);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", image1);
            map.put("face_field", "age,beauty,expression,face_shape,gender,glasses,landmark,landmark150,race,quality,eye_status,emotion,face_type,mask");
            map.put("image_type", "BASE64");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAuth(clientId, clientSecret);

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 读取网络图片
     * @param url
     * @return
     * @throws Exception
     */
    public  static byte[]  getPageData(String url) throws Exception{
        //读取网络图片 "https://img.imlianka.com/faceImg/73838dbc-1534-42e8-80e8-a03664535bdf.png
        //byte[] bytes1 = FileUtil.readFileByBytes("https://img.imlianka.com/faceImg/73838dbc-1534-42e8-80e8-a03664535bdf.png");
        //new一个URL对象
        URL url1 = new URL(url);
        //打开链接
        HttpURLConnection conn = (HttpURLConnection)url1.openConnection();
        //设置请求方式为"GET"
        conn.setRequestMethod("GET");
        //超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = readInputStream(inStream);
        return data;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }


    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;

    }

    /**
     * duqu 本地图片
     * @param path
     * @return
     * @throws Exception
     */
    public static   byte[]  getPageLocalData(String path) throws Exception{
        //创建一个字节输出流
        DataInputStream dataInputStream = null;
        try {
            dataInputStream = new DataInputStream(new FileInputStream(path));
            //创建一个字节数组  byte的长度等于二进制图片的返回的实际字节数
            byte[] b = new byte[dataInputStream.available()];
            //读取图片信息放入这个b数组
            dataInputStream.read(b);
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //关闭流
                dataInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
