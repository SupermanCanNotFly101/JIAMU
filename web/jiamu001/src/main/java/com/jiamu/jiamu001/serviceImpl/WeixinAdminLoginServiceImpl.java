package com.jiamu.jiamu001.serviceImpl;

import com.jiamu.jiamu001.Util.JWTUtil;
import com.jiamu.jiamu001.domain.ResponseBean;
import com.jiamu.jiamu001.mapper.WeixinAdminMapper;
import com.jiamu.jiamu001.pojo.WeixinAdmin;
import com.jiamu.jiamu001.service.WeixinAdminLoginService;
import org.apache.shiro.authz.UnauthorizedException;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;


@Service
public class WeixinAdminLoginServiceImpl implements WeixinAdminLoginService {


    private static Logger logger = LoggerFactory.getLogger(WeixinAdminLoginServiceImpl.class);
    @Autowired
    private WeixinAdminMapper weixinAdminMapper;


    /**
     *
     * @param userName 用户名
     * @return 用户数据
     */
    @Override
    public WeixinAdmin getAdminByName(String userName) {
        try{
            WeixinAdmin weixinAdmin = weixinAdminMapper.getAdminByName(userName);
            return weixinAdmin;
        }catch (Exception e){
            //logger.error(e.getMessage());
            return null;
        }
    }


    /**
     * 注册：需要认证admin edit
     * @param no
     * @param pwd
     * @return
     * @throws Exception
     */
    @Override
    public ResponseBean regist(String no, String pwd) throws Exception {


        WeixinAdmin weixinAdmin = new WeixinAdmin();
        weixinAdmin.setAdminName(no);
        weixinAdmin.setAdminRole("admin");
        weixinAdmin.setAdminPermission("view");
        weixinAdmin.setAdminPwd(BCrypt.hashpw(pwd,BCrypt.gensalt()));

        try{
            WeixinAdmin i = weixinAdminMapper.getAdminByName(no);
            if(i!=null){
                return new ResponseBean(400,"用户名已注册",null);
            }else{

                weixinAdminMapper.insertAdmin(weixinAdmin);
                logger.info("用户"+no+"注册成功"+"密码"+weixinAdmin.getAdminPwd());
                return new ResponseBean(200,"注册成功",null);

            }
        }catch (DataAccessException e){
            logger.error(e.getMessage());
            return null;
        }

    }


    /**
     * 登录
     * @param no
     * @param pwd
     * @return 登录是否成功
     */
    public ResponseBean login(String no,String pwd)  {
        try{
            WeixinAdmin admin = weixinAdminMapper.getAdminByName(no);
            if(admin==null){
                return new ResponseBean(403, "用户未注册", null);
            }

            if (BCrypt.checkpw(pwd, admin.getAdminPwd())) {
                return new ResponseBean(200, "登录成功", JWTUtil.sign(no, admin.getAdminPwd(),24*60*60*1000));
            } else {
                return new ResponseBean(401, "密码错误", null);
            }
        }catch (DataAccessException e){
            logger.error(e.getMessage());
            return null;
        }
    }
}
