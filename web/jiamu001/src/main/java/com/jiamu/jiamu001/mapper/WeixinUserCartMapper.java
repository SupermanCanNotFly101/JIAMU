package com.jiamu.jiamu001.mapper;


import com.jiamu.jiamu001.pojo.WeixinUserCart;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WeixinUserCartMapper {

    @Insert("insert into weixin_user_cart(userId,productId,productNum) values(#{userId},#{productId},#{productNum})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public int addCart(WeixinUserCart weixinUserCart);


    @Select("select * from weixin_user_cart where userId=#{userId}")
    public List<WeixinUserCart> getCart(int userId);

    @Delete("delete from weixin_user_cart where id=#{id}")
    public int deleteById(int id);

    @Update("update weixin_user_cart set productNum=#{productNum} where id=#{id}")
    public int updateCart(int productNum,int id);

    @Delete("delete from weixin_user_cart where productId=#{productId}")
    public int deleteByProductId(int productId);
}
