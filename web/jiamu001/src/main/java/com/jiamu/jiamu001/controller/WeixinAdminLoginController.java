package com.jiamu.jiamu001.controller;



import com.jiamu.jiamu001.domain.ResponseBean;
import com.jiamu.jiamu001.service.WeixinAdminLoginService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;


/**
 * 处理管理员登录
 */
@RestController
@RequestMapping("/admin")
public class WeixinAdminLoginController {

    @Autowired
    WeixinAdminLoginService weixinAdminLoginService;





    /**
     * 注册
     * @param no
     * @param pwd
     * @return
     * @throws Exception
     */
    @RequestMapping("/regist")
    @RequiresRoles("admin")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public ResponseBean regist(@RequestParam("no") String no, @RequestParam("pwd") String pwd) throws Exception {
        return weixinAdminLoginService.regist(no,pwd);
    }

    /**
     *
     * @param no
     * @param pwd
     * @return
     * @throws Exception
     */
    @RequestMapping("/login")
    public ResponseBean doLogin(@RequestParam("no") String no,@RequestParam("pwd") String pwd) throws Exception {
        return weixinAdminLoginService.login(no,pwd);
    }

}
