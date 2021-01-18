package com.jiamu.jiamu001.Util;


import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;

import org.apache.commons.io.IOUtils;


@Component
/**
 * 微信支付
 */
public class WxPayUtil implements WXPayConfig {

    @Value("${wxPay.certPath}")
    String certPath;
    @Value("${myWeixin.appid}")
    String appid;
    @Value("${wxPay.id}")
    String mchid;
    @Value("${wxPay.key}")
    String key;



    /** 加载证书  这里证书需要到微信商户平台进行下载*/
    private byte [] certData;

//    public WxPayUtil() throws  Exception{
////
//
//        File file = new File("/Users/zoushiyu/IdeaProjects/jiamu001/src/main/resources/cert/apiclient_cert.p12");
//        InputStream certStream = new FileInputStream(file);
//        this.certData = new byte[(int) file.length()];
//        certStream.read(this.certData);
//        certStream.close();
//    }


    public void setCertPath() throws  Exception{
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }



    @Override
    public String getAppID() {
        return appid;
    }

    @Override
    public String getMchID() {
        return mchid;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(this.certData);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }


    /**
     * 产生随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length){
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //长度为几就循环几次
        for(int i=0; i<length; ++i){
            //产生0-61的数字
            int number=random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString().toUpperCase();
    }
}
