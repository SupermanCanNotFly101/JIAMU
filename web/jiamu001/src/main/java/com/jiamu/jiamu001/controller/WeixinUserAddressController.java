package com.jiamu.jiamu001.controller;


import com.jiamu.jiamu001.pojo.WeixinUserAddress;
import com.jiamu.jiamu001.service.WeixinUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/userAddress")
@RestController
public class WeixinUserAddressController {


    @Autowired
    WeixinUserAddressService weixinUserAddressService;

    /**
     * 获取address
     * @param userId id
     * @return 地址list
     */
    @RequestMapping("/getAddress")
    public List getAddress(@RequestParam("id") int userId){
        List<WeixinUserAddress> result = weixinUserAddressService.getAddress(userId);
        return result;

    }


    /**
     * 添加地址
     * @param userId
     * @param userName
     * @param userPhone
     * @param userAddress10
     * @param userAddress11
     * @param userAddress12
     * @param userAdress2
     * @return result.put(" result ", 1);
     *         result.put("id",weixinUserAddress1.getId());
     */
    @RequestMapping("/addAddress")
    public Map addAddress(@RequestParam("id") int userId,
                          @RequestParam("name")String userName,
                          @RequestParam("phone") String userPhone,
                          @RequestParam("address10") String userAddress10,
                          @RequestParam("address11") String userAddress11,
                          @RequestParam("address12") String userAddress12,
                          @RequestParam("address2") String userAdress2){

        WeixinUserAddress weixinUserAddress = weixinUserAddressService.generateUserAddress(userId,userName,userPhone,userAddress10,userAddress11, userAddress12,userAdress2);
        WeixinUserAddress weixinUserAddress1 = weixinUserAddressService.addAddress(weixinUserAddress);
        Map result = new HashMap();
        result.put("result",1);
        result.put("id",weixinUserAddress1.getId());
        return result;
    }


    /**
     * 删除地址
     * @param id
     * @return
     */
    @RequestMapping("/deleteAddress")
    public Map deleteAddress(@RequestParam("id") int id){
        boolean deleteResult = weixinUserAddressService.deleteAddress(id);
        Map result = new HashMap();
        result.put("result",deleteResult);
        return result;
    }


    /**
     * 更新地址
     * @param userId
     * @param id
     * @param userName
     * @param userPhone
     * @param userAddress10
     * @param userAddress11
     * @param userAddress12
     * @param userAdress2
     * @return
     */
    @RequestMapping("/updateAddress")
    public Map updateAddress(@RequestParam("id") int userId,
                             @RequestParam("addressId") int id,
                             @RequestParam("name")String userName,
                             @RequestParam("phone") String userPhone,
                             @RequestParam("address10") String userAddress10,
                             @RequestParam("address11") String userAddress11,
                             @RequestParam("address12") String userAddress12,
                             @RequestParam("address2") String userAdress2){
        WeixinUserAddress weixinUserAddress = weixinUserAddressService.generateUserAddress1(id,userId,userName,userPhone,userAddress10,userAddress11, userAddress12,userAdress2);
        boolean flag = weixinUserAddressService.updateAddress(weixinUserAddress);
        Map result = new HashMap();
        result.put("result",flag);
        return result;
    }













}
