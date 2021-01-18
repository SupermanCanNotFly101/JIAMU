package com.jiamu.jiamu001.Util;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.text.Document;
import javax.swing.text.Element;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * URL请求工具类
 */
@Component
public class URLUtil {


    private static final Logger logger = LoggerFactory.getLogger(URLUtil.class);
    /**
     * 返回get请求的字符串
     * @param requestUrl
     * @param requestUrlParam
     * @return result字符串
     */
    public static String sendGet(String requestUrl, Map<String,String> requestUrlParam){
        String url = URLUtil.getUrl(requestUrl,requestUrlParam);
        String result = URLUtil.doGet(url);
        return result;
    }


    /**
     * map转URL
     * @param requestUrl
     * @param requestUrlParam
     * @return
     */
    public static String getUrl(String requestUrl, Map<String,String> requestUrlParam){
        if (requestUrlParam == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : requestUrlParam.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = requestUrl + sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;

    }

    /**
     * 模拟get请求接口返回json数据格式
     * @param url
     * @return
     */
    public static String doGet(String url){
        String result="";

        //获取httpclient对象
        HttpClient client = HttpClientBuilder.create().build();
        //获取get请求对象
        HttpGet httpGet = new HttpGet(url);
        try {
            //发起请求
            HttpResponse response = client.execute(httpGet);
            //获取响应中的数据
            HttpEntity entity = response.getEntity();
            //把entity转换成字符串
            result = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        }
        return result;
    }




}
