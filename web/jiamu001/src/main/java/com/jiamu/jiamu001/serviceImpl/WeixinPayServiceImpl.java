package com.jiamu.jiamu001.serviceImpl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.jiamu.jiamu001.Util.URLUtil;
import com.jiamu.jiamu001.Util.WxPayUtil;
import com.jiamu.jiamu001.domain.ResponseBean;
import com.jiamu.jiamu001.service.WeixinPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.github.wxpay.sdk.WXPayUtil.MD5;


@Service
public class WeixinPayServiceImpl implements WeixinPayService {


    @Value("${wxPay.ip}")
    String spbill_create_ip;
    @Value("${wxPay.notifyUrl}")
    String notify_url;
    @Value("${wxPay.tradeType}")
    String TRADETYPE;

    String pay_url="https://api.mch.weixin.qq.com/pay/unifiedorder";

    @Autowired
    WxPayUtil wxPayUtil;



    /**
     * 统一下单接口
     *
     *
     * @return
     *
     */
    @Override
    public ResponseBean wxPay(String payOrderId,int sumMoney,String openid) throws Exception {


        wxPayUtil.setCertPath();

        WXPay wxPay = new WXPay(wxPayUtil);


        try {
            //生成的随机字符串
            String nonce_str = WxPayUtil.getRandomString(32);
            //商品名称
            String body = "嘉木茶叶";

            //组装参数，用户生成统一下单接口的签名
            Map<String, String> packageParams = new HashMap<>();

            packageParams.put("appid", wxPayUtil.getAppID());
            packageParams.put("mch_id", wxPayUtil.getMchID());
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", payOrderId);//商户订单号,自己的订单ID
            packageParams.put("total_fee", sumMoney+"");//支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", notify_url);//支付成功后的回调地址
            packageParams.put("trade_type", TRADETYPE);//支付方式
            packageParams.put("openid", openid);//用户的openID，自己获取

            String s = WXPayUtil.generateSignature(packageParams, wxPayUtil.getKey());  //签名
            packageParams.put("sign",s);

            Map<String, String> respData = wxPay.unifiedOrder(packageParams);
            if (respData.get("return_code").equals("SUCCESS")){

                //返回给APP端的参数，APP端再调起支付接口
                Map<String,String> repData = new HashMap<>();
                repData.put("appId",wxPayUtil.getAppID());
                repData.put("signType","MD5");
                String mypackage = "prepay_id="+respData.get("prepay_id");
                repData.put("package",mypackage);
                repData.put("nonceStr",respData.get("nonce_str"));
                repData.put("timeStamp",String.valueOf(System.currentTimeMillis()/1000));

                String sign = WXPayUtil.generateSignature(repData, wxPayUtil.getKey());  //签名


                respData.put("sign",sign);
                respData.put("package",mypackage);
                respData.put("timestamp",repData.get("timeStamp"));
                return new ResponseBean(200,null,respData);

             }
            throw new Exception(respData.get("return_msg"));
        } catch (Exception e) {

        }
        return new ResponseBean(500,"error",null);
    }
}
