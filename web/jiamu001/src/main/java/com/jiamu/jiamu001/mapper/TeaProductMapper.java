package com.jiamu.jiamu001.mapper;


import com.jiamu.jiamu001.pojo.TeaProduct;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeaProductMapper {


    @Insert("insert into tea_product(teaName,teaPrice,teaSize,teaTaste,teaInfo,teaPhotoUrl,teaPhotoUrlList,teaType) values(#{teaName},#{teaPrice},#{teaSize},#{teaTaste},#{teaInfo},#{teaPhotoUrl},#{teaPhotoUrlList},#{teaType})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public int addProduct(TeaProduct teaProduct) throws DataAccessException;


    @Select("select * from tea_product where teaType=#{teaType}")
    public List<TeaProduct> getTea(int teaType)throws DataAccessException;


    @Delete("delete from tea_product where id=#{id}")
    public int delete(int id)throws DataAccessException;

    @Select("select * from tea_product where id=#{id}")
    public TeaProduct getTeaById(int id)throws DataAccessException;



}
