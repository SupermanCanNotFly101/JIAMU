package com.jiamu.jiamu001.service;


import com.jiamu.jiamu001.domain.ResponseBean;
import com.jiamu.jiamu001.pojo.WeixinAdmin;

public interface WeixinAdminLoginService {

    public ResponseBean regist(String no, String pwd) throws Exception;

    public ResponseBean login(String no, String pwd);

    public WeixinAdmin getAdminByName(String userName);
}
