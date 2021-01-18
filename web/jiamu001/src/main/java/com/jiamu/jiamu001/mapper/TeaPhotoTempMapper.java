package com.jiamu.jiamu001.mapper;


import com.jiamu.jiamu001.pojo.PhotoTemp;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeaPhotoTempMapper {

    @Insert("insert into photo_temp(url,realPath) values(#{url},#{realPath})")
    public  int add(PhotoTemp photoTemp) throws DataAccessException;


    @Delete("delete from photo_temp where url=#{url}")
    public int delete(String url)throws DataAccessException;

    @Select("select * from photo_temp")
    public List<PhotoTemp> select()throws DataAccessException;
}
