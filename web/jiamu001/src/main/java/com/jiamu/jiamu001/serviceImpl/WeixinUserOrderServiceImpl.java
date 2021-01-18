package com.jiamu.jiamu001.serviceImpl;

import com.jiamu.jiamu001.mapper.TeaProductMapper;
import com.jiamu.jiamu001.mapper.WeixinUserOrderListMapper;
import com.jiamu.jiamu001.mapper.WeixinUserOrderMapper;
import com.jiamu.jiamu001.mapper.WeixinUserOrderProductMapper;
import com.jiamu.jiamu001.pojo.TeaProduct;
import com.jiamu.jiamu001.pojo.WeixinUserOrder;
import com.jiamu.jiamu001.pojo.WeixinUserOrderList;
import com.jiamu.jiamu001.pojo.WeixinUserOrderProduct;
import com.jiamu.jiamu001.service.WeixinUserOrderService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Service
public class WeixinUserOrderServiceImpl implements WeixinUserOrderService {



    @Autowired
     WeixinUserOrderMapper weixinUserOrderMapper;
    @Autowired
     TeaProductMapper teaProductMapper;
    @Autowired
     WeixinUserOrderProductMapper weixinUserOrderProductMapper;
    @Autowired
     WeixinUserOrderListMapper weixinUserOrderListMapper;





    /**
     *
     * @param weixinUserOrder 订单的数据
     * @param payCart 订单商品json字符串数组
     * @return
     */
    @Override
    public int addOrder(WeixinUserOrder weixinUserOrder,String payCart) {


        weixinUserOrder.setPayStatus(0);//设置支付状态为0
        int num = weixinUserOrderMapper.addOrder(weixinUserOrder);
        int orderId = weixinUserOrder.getId();//生成的订单号

        double sumMoney = 0;//总金额，重新计算以防万一
        JSONArray orderList = JSONArray.fromObject(payCart);//购物车数据

        for(int i=0;i<orderList.size();i++) {
            int productID = (int) orderList.getJSONObject(i).get("productId");//商品id
            int productNum = (int) orderList.getJSONObject(i).get("productNum");//商品数目

            TeaProduct teaProduct = teaProductMapper.getTeaById(productID); //获取商品
            if( teaProduct==null){
                return -1; //如果商品已被下架则返回-1
            }else{
                sumMoney = sumMoney + teaProduct.getTeaPrice()*productNum;//金额

                //把商品加入表
                WeixinUserOrderProduct weixinUserOrderProduct = weixinUserOrderProductMapper.getById(productID);

                if(weixinUserOrderProduct==null){
                    weixinUserOrderProduct = new WeixinUserOrderProduct();
                    weixinUserOrderProduct.setOldId(productID); //原本商品id
                    weixinUserOrderProduct.setIsAlive(1);
                    weixinUserOrderProduct.setTeaName(teaProduct.getTeaName());
                    weixinUserOrderProduct.setTeaSize(teaProduct.getTeaSize());
                    weixinUserOrderProduct.setTeaPrice(teaProduct.getTeaPrice());
                    weixinUserOrderProduct.setTeaPhotoUrl(teaProduct.getTeaPhotoUrl());
                    int num1 = weixinUserOrderProductMapper.addOrderProduct(weixinUserOrderProduct);
                    if(num1!=1){
                        return -1; //无法插入
                    }

                }
                int newProductId = weixinUserOrderProduct.getId(); //新表的id

                //把商品插入对应的list
                WeixinUserOrderList weixinUserOrderList = new WeixinUserOrderList();
                weixinUserOrderList.setoId(orderId);
                weixinUserOrderList.setProductId(newProductId);
                weixinUserOrderList.setProductNum(productNum);
                int num2 = weixinUserOrderListMapper.addOrderList(weixinUserOrderList);
                if(num2!=1){
                    return -1; //无法插入
                }
            }

        }
        weixinUserOrderMapper.updateSumMoney(sumMoney,orderId); //更新金额
        return num;
    }


    @Override
    public Map getOrderById(int id) {
        Map result = new HashMap();

        WeixinUserOrder weixinUserOrder = weixinUserOrderMapper.getOrderById(id);
        result.put("orderId",weixinUserOrder.getOrderId());
        result.put("sumMoney",weixinUserOrder.getSumMoney());
        result.put("payStatus",weixinUserOrder.getPayStatus());
        result.put("addressStatus",weixinUserOrder.getAddressStatus());
        if(weixinUserOrder.getExpressId()==null){
            result.put("expressId",0);
        }else{
            result.put("expressId",weixinUserOrder.getExpressId());
        }

        if(weixinUserOrder.getAddressStatus()==0){
            result.put("userPhone",weixinUserOrder.getUserPhone());
        }else{
            result.put("userName",weixinUserOrder.getUserName());
            result.put("userPhone",weixinUserOrder.getUserPhone());
            result.put("userAddress",weixinUserOrder.getUserAddress());
        }
        List cart = new LinkedList();

        List<WeixinUserOrderList> orderList =  weixinUserOrderListMapper.getOrderListByOID(id);
        for(WeixinUserOrderList order:orderList){
            Map temp = new HashMap();
            temp.put("productNum",order.getProductNum());
            int productId = order.getProductId();
            WeixinUserOrderProduct weixinUserOrderProduct =weixinUserOrderProductMapper.getByNewId(productId);
            temp.put("teaName",weixinUserOrderProduct.getTeaName());
            temp.put("teaSize",weixinUserOrderProduct.getTeaSize());
            temp.put("teaPrice",weixinUserOrderProduct.getTeaPrice());
            temp.put("teaPhotoUrl",weixinUserOrderProduct.getTeaPhotoUrl());
            cart.add(temp);
        }

        result.put("cart",cart);


        return result;
    }

    @Override
    public int deleteOrder(int id) {

        weixinUserOrderListMapper.deleteOrderList(id);
        return weixinUserOrderMapper.deleteOrder(id);
    }


    @Override
    public List getOrderByUserId(int userId) {

        List<WeixinUserOrder> orderList = weixinUserOrderMapper.selectByUserId(userId);

        return WeixinUserOrderServiceImpl.getOrderInfo(orderList,weixinUserOrderListMapper,weixinUserOrderProductMapper);
    }

    @Override
    public List getAllOrder() {

        List<WeixinUserOrder> orderList = weixinUserOrderMapper.getAllOrder();
        return WeixinUserOrderServiceImpl.getOrderInfo(orderList,weixinUserOrderListMapper,weixinUserOrderProductMapper);

    }

    @Override
    public List getCompeleteOrder() {
        List<WeixinUserOrder> orderList = weixinUserOrderMapper.getCompeleteOrder();
        return WeixinUserOrderServiceImpl.getOrderInfo(orderList,weixinUserOrderListMapper,weixinUserOrderProductMapper);


    }

    static List getOrderInfo(List<WeixinUserOrder> orderList, WeixinUserOrderListMapper weixinUserOrderListMapper, WeixinUserOrderProductMapper weixinUserOrderProductMapper){
        if(orderList==null){
            return null;
        }
        List result = new LinkedList();

        //遍历每一个
        for(WeixinUserOrder order:orderList){
            Map temp = new HashMap();
            int id = order.getId();
            temp.put("id",id);
            temp.put("orderId",order.getOrderId());
            temp.put("sumMoney",order.getSumMoney());
            temp.put("status",order.getPayStatus());
            List temp1 = new LinkedList();
            List<WeixinUserOrderList> list = weixinUserOrderListMapper.getOrderListByOID(id);
            for(WeixinUserOrderList l:list){
                temp1.add( weixinUserOrderProductMapper.getByNewId(l.getProductId()).getTeaPhotoUrl() );
            }
            temp.put("cart",temp1);
            result.add(temp);
        }
        return result;

    }

    @Override
    public int updateStatus(int payStatus, int id) {

        return weixinUserOrderMapper.updateStatus(payStatus,id);

    }

    @Override
    public int updateExpressId(String expressId, int id) {
        return weixinUserOrderMapper.updateExpressId(expressId,id);
    }
}
