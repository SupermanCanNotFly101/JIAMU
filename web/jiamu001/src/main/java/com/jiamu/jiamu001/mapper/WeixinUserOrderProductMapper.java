package com.jiamu.jiamu001.mapper;


import com.jiamu.jiamu001.pojo.WeixinUserOrder;
import com.jiamu.jiamu001.pojo.WeixinUserOrderProduct;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WeixinUserOrderProductMapper {

    @Insert("insert into weixin_user_order_product(oldId,isAlive,teaName,teaPrice,teaSize,teaPhotoUrl) values(#{oldId},#{isAlive},#{teaName},#{teaPrice},#{teaSize},#{teaPhotoUrl})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public int addOrderProduct(WeixinUserOrderProduct weixinUserOrderProduct);


    @Update("update weixin_user_order_product set isAlive=0 where oldId = #{oldId}")
    public int makeNotAliveById(int oldId);

    @Update("update weixin_user_order_product set teaPhotoUrl=#{teaPhotoUrl} where oldId = #{oldId}")
    public int setNotAlivePhotoUrl(String teaPhotoUrl,int oldId);


    @Select("select * from weixin_user_order_product where oldId=#{oldId}")
    public WeixinUserOrderProduct getById(int oldId);

    @Select("select * from weixin_user_order_product where id=#{id}")
    public WeixinUserOrderProduct getByNewId(int id);


}
