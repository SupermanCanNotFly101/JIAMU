package com.jiamu.jiamu001.service;

import com.jiamu.jiamu001.pojo.WeixinUserCart;

import java.util.List;

public interface WeixinUserCartService {

    public int addCart(WeixinUserCart weixinUserCart);

    public List getCart(int userId) throws IllegalAccessException;

    public int deleteById(int id);

    public int updateCart(int productNum,int id);
}
