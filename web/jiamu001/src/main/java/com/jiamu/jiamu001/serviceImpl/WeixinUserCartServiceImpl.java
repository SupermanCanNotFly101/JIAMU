package com.jiamu.jiamu001.serviceImpl;

import com.jiamu.jiamu001.Util.JSONUtil;
import com.jiamu.jiamu001.mapper.TeaProductMapper;
import com.jiamu.jiamu001.mapper.WeixinUserCartMapper;
import com.jiamu.jiamu001.pojo.TeaProduct;
import com.jiamu.jiamu001.pojo.WeixinUserCart;
import com.jiamu.jiamu001.service.WeixinUserCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Service
public class WeixinUserCartServiceImpl implements WeixinUserCartService {

    @Autowired
    WeixinUserCartMapper weixinUserCartMapper;
    @Autowired
    TeaProductMapper teaProductMapper;


    @Override
    public int addCart(WeixinUserCart weixinUserCart) {
        int productId = weixinUserCart.getProductId();
        TeaProduct tea = teaProductMapper.getTeaById(productId);
        if(tea!=null){
            weixinUserCartMapper.addCart(weixinUserCart);
            return weixinUserCart.getId();
        }
        else{
            return -1;
        }
    }

    @Override
    public List getCart(int userId) throws IllegalAccessException {
        List resultList = new LinkedList();
        List<WeixinUserCart> cartList = weixinUserCartMapper.getCart(userId);
        int productId;
        for(WeixinUserCart cart:cartList){

            productId = cart.getProductId();
            TeaProduct teaProduct = teaProductMapper.getTeaById(productId);
            if(teaProduct!=null){
                Map map = JSONUtil.objectToMap(teaProduct);
                map.put("cartId",cart.getId());
                map.put("productNum",cart.getProductNum());
                map.put("select",cart.getSelect());
                resultList.add(map);
            }else{
                weixinUserCartMapper.deleteByProductId(productId);
            }

        }
        return resultList;
    }


    @Override
    public int deleteById(int id) {
        return weixinUserCartMapper.deleteById(id);
    }


    @Override
    public int updateCart(int productNum, int id) {
        return weixinUserCartMapper.updateCart(productNum,id);
    }
}
