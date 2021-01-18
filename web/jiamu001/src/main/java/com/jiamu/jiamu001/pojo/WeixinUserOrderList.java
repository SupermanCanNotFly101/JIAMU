package com.jiamu.jiamu001.pojo;

import java.util.Objects;

public class WeixinUserOrderList {
    private int id;
    private int oId;
    private int productId;
    private int productNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeixinUserOrderList that = (WeixinUserOrderList) o;
        return id == that.id &&
                oId == that.oId &&
                productId == that.productId &&
                productNum == that.productNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, oId, productId, productNum);
    }
}
