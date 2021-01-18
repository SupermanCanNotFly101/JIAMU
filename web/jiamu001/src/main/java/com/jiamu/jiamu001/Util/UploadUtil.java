package com.jiamu.jiamu001.Util;


import com.jiamu.jiamu001.domain.ResponseBean;
import com.jiamu.jiamu001.pojo.WeixinSwipper;
import com.jiamu.jiamu001.serviceImpl.WeixinSwipperServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class UploadUtil {

    @Value("${myUpload.absoluteImgPath}")
    String realImgPath;



    private static final Logger logger = LoggerFactory.getLogger(UploadUtil.class);


    /**
     *
     * @param files 文件流
     * @param fileList 文件目录
     * @return 404失败，200成功( map{fileName,realPath} )
     * @throws FileNotFoundException
     */
    public ResponseBean uploadFile(MultipartFile files,String fileList) throws FileNotFoundException {

        if (files.isEmpty()) {
            return new ResponseBean(404,"找不到文件",null);
        } else {

            String fileName = files.getOriginalFilename();//获取文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID().toString() + suffixName;
            String localPath = realImgPath + fileList;
            String realPath = localPath + fileName;
            Map map = new HashMap();
            map.put("fileName",fileName);
            map.put("realPath",realPath);

            File dest = new File(realPath);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
            }
            try {
                //保存文件
                files.transferTo(dest);

            } catch (IllegalStateException e) {
                logger.error(e.getMessage());

            } catch (IOException e) {
                logger.error(e.getLocalizedMessage());
            }
            return new ResponseBean(200,"ok",map);

        }

    }
}
