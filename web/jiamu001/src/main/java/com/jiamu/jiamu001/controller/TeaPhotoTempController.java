package com.jiamu.jiamu001.controller;

import com.jiamu.jiamu001.domain.ResponseBean;
import com.jiamu.jiamu001.service.TeaPhotoTempService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



/**
 * 处理上传图片
 */
@RestController
@RequestMapping("/teaPhoto")
public class TeaPhotoTempController {


    @Autowired
    TeaPhotoTempService teaPhotoTempService;

    private static Logger logger = LoggerFactory.getLogger(TeaPhotoTempController.class);


    /**
     * 上传一个文件保存在服务器指定absoluteImgPath+自定义目录(/tea/文件名）
     * @param file
     * @return {500:失败msg}{200:成功msg,data:url地址}
     * @throws Exception
     */
    @RequestMapping("/addFile")
    @RequiresRoles("admin")
    public ResponseBean addFile(@RequestParam(value = "file") MultipartFile file) throws Exception {
        return teaPhotoTempService.addPhoto(file);
    }


}
