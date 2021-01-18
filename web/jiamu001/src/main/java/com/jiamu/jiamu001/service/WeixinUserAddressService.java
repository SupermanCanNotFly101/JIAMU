package com.jiamu.jiamu001.service;

import com.jiamu.jiamu001.pojo.WeixinUserAddress;


import java.util.List;

public interface WeixinUserAddressService {


    public List<WeixinUserAddress> getAddress(int userId);

    public WeixinUserAddress addAddress(WeixinUserAddress weixinUserAddress);

    public WeixinUserAddress generateUserAddress(int userId, String userName, String userPhone, String userAddress10,String userAddress11,String userAddress12,String userAdress2);

    public WeixinUserAddress generateUserAddress1(int id, int userId, String userName, String userPhone, String userAddress10,String userAddress11,String userAddress12,String userAdress2);

    public boolean deleteAddress(int id);

    public  boolean updateAddress(WeixinUserAddress weixinUserAddress);

}
