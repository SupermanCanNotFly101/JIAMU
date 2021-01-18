package com.jiamu.jiamu001.service;

import com.jiamu.jiamu001.domain.ResponseBean;
import com.jiamu.jiamu001.pojo.TeaProduct;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

public interface TeaProductService {

    public boolean addTea(TeaProduct teaProduct);

    public List<TeaProduct> getTea(int teaType);

    public int delete(TeaProduct teaProduct);
}
