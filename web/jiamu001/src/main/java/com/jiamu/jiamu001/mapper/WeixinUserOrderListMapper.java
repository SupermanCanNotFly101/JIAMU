package com.jiamu.jiamu001.mapper;


import com.jiamu.jiamu001.pojo.WeixinUserOrderList;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WeixinUserOrderListMapper {

    @Insert("insert weixin_user_order_list(oId,productId,productNum) values(#{oId},#{productId},#{productNum})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public int addOrderList(WeixinUserOrderList weixinUserOrderList);


    @Select("select * from weixin_user_order_list where oId=#{oId}")
    public List<WeixinUserOrderList> getOrderListByOID(int oId);


    @Delete("delete from weixin_user_order_list where oId=#{oId}")
    public int deleteOrderList(int oId);
}
