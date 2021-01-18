package com.jiamu.jiamu001.serviceImpl;

import com.jiamu.jiamu001.Util.URLUtil;
import com.jiamu.jiamu001.Util.WxPayUtil;
import com.jiamu.jiamu001.mapper.WeixinUserMapper;
import com.jiamu.jiamu001.pojo.WeixinUser;
import com.jiamu.jiamu001.service.WeixinUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONObject;



@Service
public class WeixinUserLoginServiceImpl implements WeixinUserLoginService {


    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session?";
    private static final Logger logger = LoggerFactory.getLogger(WeixinUserLoginServiceImpl.class);
    @Autowired
    private  WeixinUserMapper weixinUserMapper;
    @Value("${myWeixin.appid}")
    String appid;
    @Value("${myWeixin.secret}")
    String secret;






    /**
     * 根据code返回openid
     * @param code
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getUserInfoMap(String code) throws Exception {
        Map<String, Object> userInfoMap = new HashMap<>();
        logger.info("Start get SessionKey，loginRequest的数据为：" + code);
        JSONObject result = WeixinUserLoginServiceImpl.getSessionKeyOrOpenId(code,appid,secret);
        return result;
    }


    /**
     * 通过code获取openid
     * @param code
     * @return JSONOBJECT
     */
    public static JSONObject getSessionKeyOrOpenId(String code,String appid,String secret){
        //微信端登录code
        String wxCode = code;
        Map<String,String> requestUrlParam = new HashMap<String, String>();
        requestUrlParam.put( "appid", appid );//小程序appId
        requestUrlParam.put( "secret",secret);
        requestUrlParam.put( "js_code",wxCode );//小程序端返回的code
        requestUrlParam.put( "grant_type","authorization_code" );//默认参数
        //发送post请求读取调用微信接口获取openid用户唯一标识
        JSONObject jsonObject = JSONObject.fromObject( URLUtil.sendGet(REQUEST_URL,requestUrlParam ));
        return jsonObject;
    }


    /**
     * 根据openid判断注册是否
     * @param openid
     * @return
     */
    @Override
    public WeixinUser regUser(String openid) {
        WeixinUser weixinUser = weixinUserMapper.hasOpenid(openid);
        if(weixinUser==null){
            WeixinUser user = new WeixinUser();
            user.setOpenId(openid);
            weixinUserMapper.insertUser(user);
            return user;
        }else{
            return weixinUser;
        }
    }
}
