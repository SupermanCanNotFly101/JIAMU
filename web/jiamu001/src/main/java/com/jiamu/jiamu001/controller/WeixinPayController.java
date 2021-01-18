package com.jiamu.jiamu001.controller;


import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.jiamu.jiamu001.Util.WxPayUtil;
import com.jiamu.jiamu001.domain.ResponseBean;
import com.jiamu.jiamu001.mapper.WeixinUserOrderMapper;
import com.jiamu.jiamu001.service.WeixinPayService;
import com.jiamu.jiamu001.service.WeixinUserOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;


@RequestMapping("/wxPay")
@RestController
public class WeixinPayController {


    @Autowired
    WeixinPayService weixinPayService;
    @Autowired
    WxPayUtil wxPayUtil;
    @Autowired
    WeixinUserOrderMapper weixinUserOrderMapper;


    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 统一下单
     */
    @RequestMapping("/prepay")
    public ResponseBean wxPay(@RequestParam("orderId") String payOrderId, @RequestParam("sumMoney") int sumMoney, @RequestParam("openId") String openid) throws Exception {
        return weixinPayService.wxPay(payOrderId, sumMoney, openid);
    }



    @RequestMapping("notify")
    public String weixinotify(HttpServletRequest request,
                            HttpServletResponse response) {
        logger.info("进入微信支付异步通知");
        String resXml="";
        try{
            //
            InputStream is = request.getInputStream();
            //将InputStream转换成String
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            resXml=sb.toString();
            logger.info("微信支付异步通知请求包: {}", resXml);
            return payBack(resXml);
        }catch (Exception e){
            logger.error("微信支付回调通知失败",e);
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return result;
        }
    }




    public String payBack(String notifyData) {
        logger.info("payBack() start, notifyData={}", notifyData);
        String xmlBack="";
        Map<String, String> notifyMap = null;
        try {
            WXPay wxpay = new WXPay(wxPayUtil);

            notifyMap = WXPayUtil.xmlToMap(notifyData);         // 转换成map
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                // 签名正确
                // 进行处理。
                // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
                String return_code = notifyMap.get("return_code");//状态
                String out_trade_no = notifyMap.get("out_trade_no");//订单号

                if (out_trade_no == null) {
                    logger.info("微信支付回调失败订单号: {}", notifyMap);
                    xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    return xmlBack;
                }

                // 业务逻辑处理 ****************************


                weixinUserOrderMapper.updateStatusbyOrderId(1,out_trade_no);


                logger.info("微信支付回调成功订单号: {}", notifyMap);
                xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[SUCCESS]]></return_msg>" + "</xml> ";
                return xmlBack;
            } else {
                logger.error("微信支付回调通知签名错误");
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
            logger.error("微信支付回调通知失败",e);
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        return xmlBack;
    }

}
