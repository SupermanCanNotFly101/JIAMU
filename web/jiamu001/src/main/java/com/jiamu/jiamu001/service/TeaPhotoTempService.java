package com.jiamu.jiamu001.service;

import com.jiamu.jiamu001.domain.ResponseBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

public interface TeaPhotoTempService {

    public ResponseBean addPhoto(MultipartFile file) throws FileNotFoundException;

    public int deleteTemp(String url,String mainUrl);

    public int deleteFile();

    public int deleteProductImg(String url);
}
