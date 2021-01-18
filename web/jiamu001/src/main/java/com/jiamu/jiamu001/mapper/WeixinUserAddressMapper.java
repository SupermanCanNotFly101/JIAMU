package com.jiamu.jiamu001.mapper;


import com.jiamu.jiamu001.pojo.WeixinUserAddress;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WeixinUserAddressMapper {

    @Select("select * from weixin_user_address where userId=#{userId}")
    public List<WeixinUserAddress> getAddress(@Param("userId") int userId);


    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into weixin_user_address(userId,userName,userPhone,userAddress1a,userAddress1b,userAddress1c,userAddress2) values(#{userId},#{userName},#{userPhone},#{userAddress1a},#{userAddress1b},#{userAddress1c},#{userAddress2})")
    public int addAddress(WeixinUserAddress weixinUserAddress);


    @Delete("Delete from weixin_user_address where id = #{id}")
    public int deleteAddress(@Param("id") int id);

    @Update("update weixin_user_address set userId=#{userId},userName=#{userName},userPhone=#{userPhone},userAddress1a=#{userAddress1a},userAddress1b=#{userAddress1b},userAddress1c=#{userAddress1c},userAddress2=#{userAddress2} where id=#{id}")
    public int updateAddress(WeixinUserAddress weixinUserAddress);

}
