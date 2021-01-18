package com.jiamu.jiamu001.mapper;


import com.jiamu.jiamu001.pojo.WeixinUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WeixinUserMapper {

    @Select("select * from weixin_user where openId=#{openid}")
    public WeixinUser hasOpenid(@Param("openid") String openid);


    @Insert("insert into weixin_user(openId) values(#{openId})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public int insertUser(WeixinUser weixinUser);

}
