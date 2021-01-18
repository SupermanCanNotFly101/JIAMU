package com.jiamu.jiamu001.service;



import com.jiamu.jiamu001.pojo.WeixinUser;

import java.util.Map;


public interface WeixinUserLoginService {

    Map<String, Object> getUserInfoMap(String code) throws Exception;

    WeixinUser regUser(String openid);
}
