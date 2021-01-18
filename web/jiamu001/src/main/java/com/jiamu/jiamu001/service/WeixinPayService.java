package com.jiamu.jiamu001.service;

import com.jiamu.jiamu001.domain.ResponseBean;

import javax.servlet.http.HttpServletRequest;

public interface WeixinPayService {

    public ResponseBean wxPay(String payOrderId,int sumMoney,String openid) throws Exception;

}
