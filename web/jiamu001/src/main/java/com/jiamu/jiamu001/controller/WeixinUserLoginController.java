package com.jiamu.jiamu001.controller;


import com.jiamu.jiamu001.pojo.WeixinUser;
import com.jiamu.jiamu001.service.WeixinUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/userLogin")
public class WeixinUserLoginController {

    @Autowired
    private WeixinUserLoginService weixinUserLoginService;

    /**
     * 用户登录
     * @param code
     * @return WeixinUser
     * @throws Exception
     */
    @RequestMapping("/login")
    public WeixinUser doLogin(@RequestParam("code") String code) throws Exception {
        Map<String,Object> userInfo = weixinUserLoginService.getUserInfoMap(code);
        String openid = (String) userInfo.get("openid");
        return weixinUserLoginService.regUser(openid);
    }
}
