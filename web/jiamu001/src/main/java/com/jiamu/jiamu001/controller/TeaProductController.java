package com.jiamu.jiamu001.controller;

import com.jiamu.jiamu001.domain.ResponseBean;
import com.jiamu.jiamu001.pojo.TeaProduct;
import com.jiamu.jiamu001.service.TeaPhotoTempService;
import com.jiamu.jiamu001.service.TeaProductService;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.*;

@RestController
@RequestMapping("/tea")
public class TeaProductController {

    @Autowired
    TeaProductService teaProductService;
    @Autowired
    TeaPhotoTempService teaPhotoTempService;

    /**
     * 插入商品
     * @param teaProduct
     * @return
     */
    @RequestMapping("/addTea")
    @RequiresRoles("admin")
    public ResponseBean addTea(TeaProduct teaProduct){
        if(teaProductService.addTea(teaProduct)){
            int num = teaPhotoTempService.deleteTemp(teaProduct.getTeaPhotoUrlList(),teaProduct.getTeaPhotoUrl());
            teaPhotoTempService.deleteFile();
            return new ResponseBean(200,"上传成功",null);
        }else{
            teaPhotoTempService.deleteFile();
            return new ResponseBean(500,"添加数据库错误",null);
        }

    }


    /**
     * 查询商品
     * @param teaType
     * @return
     */
    @RequestMapping("/getTea")
    public List getTea(@RequestParam("teaType") int teaType){
        return teaProductService.getTea(teaType);
    }


    /**
     * 删除商品
     * @param tea
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping("/deleteTea")
    @RequiresRoles("admin")
    public Object deleteTea(@RequestParam("tea") String tea){
        JSONObject a = JSONObject.fromObject(tea);
        TeaProduct teaProduct = (TeaProduct)JSONObject.toBean(a,TeaProduct.class);
        teaProductService.delete(teaProduct);
        return new ResponseBean(200,"删除成功",null);

    }






}
