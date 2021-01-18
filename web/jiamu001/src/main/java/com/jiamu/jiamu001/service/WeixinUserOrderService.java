package com.jiamu.jiamu001.service;

import com.jiamu.jiamu001.pojo.WeixinUserOrder;

import java.util.List;

public interface WeixinUserOrderService {

    public int addOrder(WeixinUserOrder weixinUserOrder,String payCart);

    public  Object getOrderById(int id);

    public int deleteOrder(int id);

    public List getOrderByUserId(int userId);

    public List getAllOrder();

    public List getCompeleteOrder();

    public int updateStatus(int payStatus,int id);

    public int updateExpressId(String expressId,int id);


}
