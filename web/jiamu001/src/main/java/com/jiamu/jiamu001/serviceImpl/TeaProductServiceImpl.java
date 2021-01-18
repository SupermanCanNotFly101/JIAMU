package com.jiamu.jiamu001.serviceImpl;

import com.jiamu.jiamu001.Util.JSONUtil;
import com.jiamu.jiamu001.Util.UploadUtil;
import com.jiamu.jiamu001.domain.ResponseBean;
import com.jiamu.jiamu001.mapper.TeaPhotoTempMapper;
import com.jiamu.jiamu001.mapper.TeaProductMapper;
import com.jiamu.jiamu001.mapper.WeixinUserOrderProductMapper;
import com.jiamu.jiamu001.pojo.TeaProduct;
import com.jiamu.jiamu001.service.TeaPhotoTempService;
import com.jiamu.jiamu001.service.TeaProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;


@Service
public class TeaProductServiceImpl implements TeaProductService {

    @Autowired
    TeaProductMapper teaProductMapper;
    @Autowired
    TeaPhotoTempMapper teaPhotoTempMapper;
    @Autowired
    TeaPhotoTempService teaPhotoTempService;
    @Autowired
    WeixinUserOrderProductMapper weixinUserOrderProductMapper;
    @Value("${myUpload.imgPath}")
    String UrlImgPath;
    @Value("${my.webUrl}")
    String webUrl;
    @Autowired
    UploadUtil uploadUtil;
    @Value("${NotAlive.filePath}")
    String fileName;

    private static Logger logger = LoggerFactory.getLogger(TeaProductServiceImpl.class);


    /**
     * 插入商品到数据库
     * @param teaProduct
     * @return
     */
    @Override
    public boolean addTea(TeaProduct teaProduct) {

        try{
            teaProductMapper.addProduct(teaProduct);
            return true;
        }catch (DataAccessException e){
            logger.error(e.getMessage());
            return false;
        }
    }


    /**
     * 查询
     * @param teaType
     * @return
     */
    @Override
    public List<TeaProduct> getTea(int teaType) {
        try{
            return teaProductMapper.getTea(teaType);
        }catch (DataAccessException e){
            logger.error(e.getMessage());
            return null;
        }
    }


    /**
     *
     * @param teaProduct
     * @return
     * @throws UnknownHostException
     */
    @Override
    public int delete(TeaProduct teaProduct){
        int oldId = teaProduct.getId();
        if((weixinUserOrderProductMapper.getById(oldId))!=null){
            String relativePath = webUrl+UrlImgPath+fileName; //url地址
            weixinUserOrderProductMapper.makeNotAliveById(oldId);
            weixinUserOrderProductMapper.setNotAlivePhotoUrl(relativePath,oldId);
        }

        String mainImg = teaProduct.getTeaPhotoUrl();
        teaPhotoTempService.deleteProductImg(mainImg);
        String detailImg = teaProduct.getTeaPhotoUrlList();
        List<String> ImgList = JSONUtil.getUrlList(detailImg);
        for(String temp:ImgList){
            teaPhotoTempService.deleteProductImg(temp);
        }
        teaProductMapper.delete(teaProduct.getId());
        return 1;

    }
}
