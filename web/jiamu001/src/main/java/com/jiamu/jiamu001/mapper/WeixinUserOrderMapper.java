package com.jiamu.jiamu001.mapper;


import com.jiamu.jiamu001.pojo.WeixinUserOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WeixinUserOrderMapper {


    @Insert("insert into weixin_user_order(orderId,sumMoney,payStatus,addressStatus,userId,userName,userPhone,userAddress) values(#{orderId},#{sumMoney},#{payStatus},#{addressStatus},#{userId},#{userName},#{userPhone},#{userAddress})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public int addOrder(WeixinUserOrder weixinUserOrder);

    @Update("update weixin_user_order set sumMoney=#{sumMoney} where id = #{id}")
    public int updateSumMoney(double sumMoney,int id);


    @Select("select * from weixin_user_order where id=#{id}")
    public WeixinUserOrder getOrderById(int id);


    @Delete("delete from weixin_user_order where id=#{id}")
    public int deleteOrder(int id);

    @Select("select * from weixin_user_order where userId=#{userId}")
    public List<WeixinUserOrder> selectByUserId(int userid);

    @Select("select * from weixin_user_order where payStatus<4")
    public List<WeixinUserOrder> getAllOrder();

    @Select("select * from weixin_user_order where payStatus=4")
    public List<WeixinUserOrder> getCompeleteOrder();

    @Update("update weixin_user_order set payStatus=#{payStatus} where id = #{id}")
    public int updateStatus(int payStatus,int id);

    @Update("update weixin_user_order set expressId=#{expressId} where id = #{id}")
    public int updateExpressId(String expressId,int id);

    @Update("update weixin_user_order set payStatus=#{payStatus} where orderId = #{id}")
    public int updateStatusbyOrderId(int payStatus,String id);


}
