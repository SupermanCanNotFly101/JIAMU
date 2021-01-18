package com.jiamu.jiamu001.pojo;

import java.util.Objects;

public class WeixinUserCart {
    private int id;
    private int userId;
    private int productId;
    private int productNum;
    private int select;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeixinUserCart that = (WeixinUserCart) o;
        return id == that.id &&
                userId == that.userId &&
                productId == that.productId &&
                productNum == that.productNum &&
                select == that.select;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productId, productNum, select);
    }
}
