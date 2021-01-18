package com.jiamu.jiamu001.serviceImpl;

import com.jiamu.jiamu001.mapper.WeixinUserAddressMapper;
import com.jiamu.jiamu001.pojo.WeixinUserAddress;
import com.jiamu.jiamu001.service.WeixinUserAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeixinUserAddressServiceImpl implements WeixinUserAddressService {


    @Autowired
    private WeixinUserAddressMapper weixinUserAddressMapper;
    private static final Logger logger = LoggerFactory.getLogger(WeixinUserAddressServiceImpl.class);


    /**
     *
     * @param userId
     * @return
     */
    @Override
    public List<WeixinUserAddress> getAddress(int userId) {

        List<WeixinUserAddress> result = weixinUserAddressMapper.getAddress(userId);
        logger.info("用户地址数据数"+result.size());
        return result;
    }


    @Override
    public WeixinUserAddress addAddress(WeixinUserAddress weixinUserAddress) {
        int num = weixinUserAddressMapper.addAddress(weixinUserAddress);
        logger.info("插入用户地址成功，id为"+weixinUserAddress.getId());
        //logger.info(weixinUserAddress.getuserAddress1a()+weixinUserAddress.getId());
        return weixinUserAddress;

    }

    @Override
    public WeixinUserAddress generateUserAddress(int userId, String userName, String userPhone, String userAddress10,String userAddress11,String userAddress12, String userAdress2) {
        WeixinUserAddress weixinUserAddress = new WeixinUserAddress();
        weixinUserAddress.setUserId(userId);
        weixinUserAddress.setUserName(userName);
        weixinUserAddress.setUserAddress2(userAdress2);
        weixinUserAddress.setUserPhone(userPhone);
        //logger.info("1:"+userAddress10+"2:"+userAddress11,"3:"+userAddress12);
        weixinUserAddress.setUserAddress1a(userAddress10);
        weixinUserAddress.setUserAddress1b(userAddress11);
        weixinUserAddress.setUserAddress1c(userAddress12);
        return weixinUserAddress;
    }

    @Override
    public boolean deleteAddress(int id) {
        int num = weixinUserAddressMapper.deleteAddress(id);
        if(num==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public WeixinUserAddress generateUserAddress1(int id, int userId, String userName, String userPhone, String userAddress10, String userAddress11, String userAddress12, String userAdress2) {
        WeixinUserAddress weixinUserAddress = new WeixinUserAddress();
        weixinUserAddress.setUserId(userId);
        weixinUserAddress.setUserName(userName);
        weixinUserAddress.setUserAddress2(userAdress2);
        weixinUserAddress.setUserPhone(userPhone);
        weixinUserAddress.setId(id);
        //logger.info("1:"+userAddress10+"2:"+userAddress11,"3:"+userAddress12);
        weixinUserAddress.setUserAddress1a(userAddress10);
        weixinUserAddress.setUserAddress1b(userAddress11);
        weixinUserAddress.setUserAddress1c(userAddress12);
        return weixinUserAddress;
    }

    @Override
    public boolean updateAddress(WeixinUserAddress weixinUserAddress) {
        int num = weixinUserAddressMapper.updateAddress(weixinUserAddress);
        if(num==1){
            return true;
        }else{
            return false;
        }
    }
}