package com.jiamu.jiamu001.controller;

import com.jiamu.jiamu001.domain.ResponseBean;
import com.jiamu.jiamu001.pojo.WeixinSwipper;
import com.jiamu.jiamu001.service.WeixinSwipperService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 轮播图管理类
 */
@RestController
@RequestMapping("/swipper")
public class WeixinSwipperController {


    @Autowired
    WeixinSwipperService weixinSwipperService;


    /**
     *
     * @param files
     * @return {500:失败msg}{200:成功msg,data:url地址}
     * @throws FileNotFoundException
     */
    @RequestMapping("/addSwipper")
    @RequiresRoles("admin")
    public ResponseBean addWithPic(@RequestParam(value = "file") MultipartFile files) throws FileNotFoundException {
       return weixinSwipperService.uploadFile(files);
    }


    /**
     * 删除swipper
     * @param url
     * @return {500,msg} {200,msg}
     */
    @RequestMapping("/deleteSwipper")
    @RequiresRoles("admin")
    public  ResponseBean deleteSwipper(@RequestParam("url") String url) throws FileNotFoundException {
        return weixinSwipperService.deleteSwipper(url);
    }


    /**
     * 获取swipper，不需要权限
     * @return swipper的list
     */
    @RequestMapping("/getSwipper")
    public List<WeixinSwipper> getSwipper(){
        return weixinSwipperService.getSwipper();
    }

}
