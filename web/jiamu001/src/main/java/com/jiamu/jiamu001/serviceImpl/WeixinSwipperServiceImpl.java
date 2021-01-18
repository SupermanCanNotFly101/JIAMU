package com.jiamu.jiamu001.serviceImpl;

import com.jiamu.jiamu001.Util.UploadUtil;
import com.jiamu.jiamu001.domain.ResponseBean;
import com.jiamu.jiamu001.mapper.WeixinSwipperMapper;
import com.jiamu.jiamu001.pojo.WeixinSwipper;
import com.jiamu.jiamu001.service.WeixinSwipperService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;
import java.net.InetAddress;
import java.io.FileNotFoundException;

@Service
public class WeixinSwipperServiceImpl implements WeixinSwipperService {


    @Value("${myUpload.imgPath}")
    String UrlImgPath;
    @Value("${my.webUrl}")
    String webUrl;

    private static final Logger logger = LoggerFactory.getLogger(WeixinSwipperServiceImpl.class);
    @Autowired
    WeixinSwipperMapper weixinSwipperMapper;
    @Autowired
    UploadUtil uploadUtil;

    final String fileList = "swipper/";


    /**
     * 上传照片
     * @param files
     * @return
     * @throws FileNotFoundException
     */
    @Override
    public ResponseBean uploadFile(MultipartFile files) throws FileNotFoundException {

        ResponseBean result = uploadUtil.uploadFile(files,fileList);
        if(result.getCode()==200) {
            Map map = (Map) result.getData();
            String fileName = (String) map.get("fileName");
            String realPath = (String) map.get("realPath");
            String UrlPath = webUrl+UrlImgPath+fileList+fileName;//URL地址
            logger.info("文件url地址为"+UrlPath);

            WeixinSwipper weixinSwipper = new WeixinSwipper();
            weixinSwipper.setUrl(UrlPath);
            weixinSwipper.setRealPath(realPath);
            try{
                int num = weixinSwipperMapper.inserSwipper(weixinSwipper);
                if(num == 1){
                    //文件存放的相对路径(一般存放在数据库用于img标签的src)
                    //logger.info("文件"+Url+"存入数据库成功");
                    return new ResponseBean(200,"上传成功",UrlPath);

                }else{
                    return new ResponseBean(500,"上传数据库失败",null);
                }
            }catch (DataAccessException e){
                logger.error(e.getMessage());
                return null;
            }

        }else {
            return new ResponseBean(500,"上传失败",null);
        }

    }


    /**
     * 删除照片
     * @param url
     * @return
     */
    @Override
    public ResponseBean deleteSwipper(String url) throws FileNotFoundException {
        try{
            String real = weixinSwipperMapper.getRealPathByUrl(url);
            File file = new File(real);
            file.delete();
            weixinSwipperMapper.deleteSwipper(url);
            return new ResponseBean(200,"删除成功",null);
        }catch (DataAccessException e){
            logger.error(e.getMessage());
            return new ResponseBean(500,"删除失败",null);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseBean(500,"删除失败",null);
        }

    }


    /**
     * 获取swipperList
     * @return
     */
    @Override
    public List<WeixinSwipper> getSwipper() {
        try{
            return weixinSwipperMapper.getSwipper();
        }catch (DataAccessException e){
            logger.error(e.getMessage());
            return null;
        }

    }
}
