package com.jiamu.jiamu001.controller;


import com.jiamu.jiamu001.pojo.WeixinUserCart;
import com.jiamu.jiamu001.service.WeixinUserCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class WeixinUserCartController {

    @Autowired
    WeixinUserCartService weixinUserCartService;




    @RequestMapping("/addCart")
    public Map addCart(WeixinUserCart weixinUserCart){

        int cartId = weixinUserCartService.addCart(weixinUserCart);
        Map map = new HashMap();
        if(cartId==-1){
            map.put("result",0);
            return map;
        }else{
            map.put("result",1);
            map.put("cartId",cartId);
            return map;
        }


    }




    @RequestMapping("/getCart")
    public Object getCart(@RequestParam("userId") int userId) throws IllegalAccessException {
        return weixinUserCartService.getCart(userId);
    }

    @RequestMapping("/deleteCart")
    public String deleteCart(@RequestParam("cartId") int id){
        if(weixinUserCartService.deleteById(id)==1){
            return "ok";
        }else{
            return "false";
        }
    }


    @RequestMapping("/updateCart")
    public Object updateCart(@RequestParam("id")int id,@RequestParam("productNum") int productNum ){
        int num = weixinUserCartService.updateCart(productNum,id);
        return num;
    }
}
