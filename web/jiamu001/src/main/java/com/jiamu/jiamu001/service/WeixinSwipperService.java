package com.jiamu.jiamu001.service;

import com.jiamu.jiamu001.domain.ResponseBean;
import com.jiamu.jiamu001.pojo.WeixinSwipper;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

public interface WeixinSwipperService {

    ResponseBean uploadFile(MultipartFile files) throws FileNotFoundException;

    ResponseBean deleteSwipper(String url) throws FileNotFoundException;

    List<WeixinSwipper> getSwipper();
}
