package com.jiamu.jiamu001.mapper;

import com.jiamu.jiamu001.pojo.WeixinAdmin;
import com.jiamu.jiamu001.pojo.WeixinSwipper;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface WeixinSwipperMapper {

    @Insert("insert into weixin_swipper(url,realPath) values(#{url},#{realPath})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public int inserSwipper(WeixinSwipper weixinSwipper)throws DataAccessException;


    @Delete("delete from weixin_swipper where url=#{url}")
    public int deleteSwipper(String url)throws DataAccessException;


    @Select("select * from weixin_swipper")
    public List<WeixinSwipper> getSwipper()throws DataAccessException;


    @Select("select realPath from weixin_swipper where Url=#{url}")
    public String getRealPathByUrl(String url)throws DataAccessException;

}
