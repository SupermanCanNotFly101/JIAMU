package com.jiamu.jiamu001.controller;


import com.jiamu.jiamu001.pojo.WeixinUserOrder;
import com.jiamu.jiamu001.service.WeixinUserOrderService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class WeixinUserOrderController {


    @Autowired
    WeixinUserOrderService weixinUserOrderService;


    @RequestMapping("/createOrder")
    public Map createOrder(WeixinUserOrder weixinUserOrder, @RequestParam("payCart") String payCart){

        Map result = new HashMap();
        int num = weixinUserOrderService.addOrder(weixinUserOrder,payCart);
        if(num==1){
            result.put("result",1);
        }else{
            result.put("result",0);
        }
        return result;
    }



    @RequestMapping("/getOrderById")
    public Object getOrderById(@RequestParam("id") int id){

        return weixinUserOrderService.getOrderById(id);


    }



    @RequestMapping("/deleteOrder")
    public String deleteOrder(@RequestParam("id") int id){
        int num = weixinUserOrderService.deleteOrder(id);
        if(num==1){
            return "ok";
        }else{
            return "false";
        }

    }



    @RequestMapping("/getOrderByUserId")
    public List getOrderByUserId(@RequestParam("userId") int userId){

        return weixinUserOrderService.getOrderByUserId(userId);

    }


    @RequiresRoles("admin")
    @RequestMapping("/getAllOrder")
    public List getAllOrder(){

        return weixinUserOrderService.getAllOrder();

    }


    @RequestMapping("/getCompleteOrder")
    public List getCompleteOrder(){
        return weixinUserOrderService.getCompeleteOrder();
    }


    @RequiresRoles("admin")
    @RequestMapping("/updateOrderState")
    public String updateOrderState(@RequestParam("payStatus") int payStatus,@RequestParam("id") int id,@RequestParam("code") String code){

        int num = weixinUserOrderService.updateStatus(payStatus,id);
        if(num==1){
            return "ok";
        }else{
            return "false";
        }

    }

    @RequiresRoles("admin")
    @RequestMapping("/updateexpress")
    public String updateexpress(@RequestParam("expressId") String expressId,@RequestParam("id") int id,@RequestParam("code") String code){

        int num = weixinUserOrderService.updateExpressId(expressId,id);
        if(num==1){
            return "ok";
        }else{
            return "false";
        }
    }




}
