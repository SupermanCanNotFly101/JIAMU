package com.jiamu.jiamu001.mapper;

import com.jiamu.jiamu001.pojo.WeixinAdmin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Mapper
@Repository

public interface WeixinAdminMapper {

    @Insert("insert into weixin_admin(adminName,adminPwd,adminRole,adminPermission) values(#{adminName},#{adminPwd},#{adminRole},#{adminPermission})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public int insertAdmin(WeixinAdmin weixinAdmin)throws DataAccessException;


    @Select("select * from weixin_admin where adminName=#{adminName}")
    public  WeixinAdmin getAdminByName(String adminName)throws DataAccessException;

}
