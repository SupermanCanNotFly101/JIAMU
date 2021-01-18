package com.jiamu.jiamu001.serviceImpl;

import com.jiamu.jiamu001.Util.JSONUtil;
import com.jiamu.jiamu001.Util.UploadUtil;
import com.jiamu.jiamu001.domain.ResponseBean;
import com.jiamu.jiamu001.mapper.TeaPhotoTempMapper;
import com.jiamu.jiamu001.pojo.PhotoTemp;
import com.jiamu.jiamu001.pojo.WeixinSwipper;
import com.jiamu.jiamu001.service.TeaPhotoTempService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service
public class TeaPhotoTempServiceImpl implements TeaPhotoTempService {

    @Value("${myUpload.imgPath}")
    String UrlImgPath;
    @Value("${my.webUrl}")
    String webUrl;
    @Autowired
    UploadUtil uploadUtil;
    @Autowired
    TeaPhotoTempMapper teaPhotoTempMapper;

    private static Logger logger = LoggerFactory.getLogger(TeaPhotoTempServiceImpl.class);
    String fileList="tea/";


    /**
     * 上传到服务器
     * @param file
     * @return
     */
    @Override
    public ResponseBean addPhoto(MultipartFile file) throws FileNotFoundException {

        ResponseBean result = uploadUtil.uploadFile(file,fileList);
        if(result.getCode()==200) {

            Map map = (Map) result.getData();
            String fileName = (String) map.get("fileName");
            String realPath = (String) map.get("realPath");
            String UrlPath = webUrl+UrlImgPath+fileList+fileName;//URL地址
            logger.info("文件url地址为"+UrlPath);

            PhotoTemp photoTemp = new PhotoTemp();
            photoTemp.setUrl(UrlPath);
            photoTemp.setRealPath(realPath);
            try{
                int num = teaPhotoTempMapper.add(photoTemp);
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
     * 删除这个商品的图片
     * @param url 详细图一组url的json数组
     * @param mainUrl  主图url字符串
     * @return 从临时数据库中删除的主图的数目
     */
    @Override
    public int deleteTemp(String url,String mainUrl) {

        int num = 1;
        List<String> urlList = JSONUtil.getUrlList(url);//分成一组url的list
        try{
            teaPhotoTempMapper.delete(mainUrl);
            for(String temp : urlList){
                num += teaPhotoTempMapper.delete(temp);
            }
            return num;
        }catch (DataAccessException e){
            logger.error(e.getMessage());
            return 0;
        }

    }


    /**
     * 处理完临时数据库后删除没用的图片
     * @return 删除的数量
     */
    @Override
    public int deleteFile() {
        try{
            List<PhotoTemp> pohotoList = teaPhotoTempMapper.select(); //所有没用的
            if(pohotoList!=null){
                Iterator<PhotoTemp> iterator = pohotoList.iterator();
                while(iterator.hasNext()){
                    PhotoTemp photoTemp = iterator.next();
                    String real = photoTemp.getRealPath(); //图片真实地址
                    String url = photoTemp.getUrl(); //URL地址
                    File fileTemp = new File(real); //图片流
                    try{
                        fileTemp.delete();
                    }catch (Exception e){
                        logger.error(e.getMessage());
                    }
                    teaPhotoTempMapper.delete(url); //从数据库删除
                }
            }
            return pohotoList.size();

        }catch (DataAccessException e){
            logger.error(e.getMessage());
            return 0;
        }

    }


    /**
     * 根据url删除服务器图片
     * @param url
     * @return
     */
    @Override
    public int deleteProductImg(String url) {

        String imgName = url.substring(url.lastIndexOf("/"));
        String realPath = webUrl + "tea" + imgName; //真实地址
        File fileTemp = new File(realPath); //图片流
        try{
            fileTemp.delete();
        }catch (Exception e){

        }
        return 0;
    }
}
