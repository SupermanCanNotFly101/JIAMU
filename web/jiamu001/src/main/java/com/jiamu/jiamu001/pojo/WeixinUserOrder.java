package com.jiamu.jiamu001.pojo;

import java.util.Objects;

public class WeixinUserOrder {
    private int id;
    private String orderId;
    private double sumMoney;
    private int payStatus;
    private int addressStatus;
    private int userId;
    private String userName;
    private String userPhone;
    private String userAddress;
    private String expressId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getAddressStatus() {
        return addressStatus;
    }

    public void setAddressStatus(int addressStatus) {
        this.addressStatus = addressStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeixinUserOrder that = (WeixinUserOrder) o;
        return id == that.id &&
                Double.compare(that.sumMoney, sumMoney) == 0 &&
                payStatus == that.payStatus &&
                addressStatus == that.addressStatus &&
                userId == that.userId &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(userPhone, that.userPhone) &&
                Objects.equals(userAddress, that.userAddress) &&
                Objects.equals(expressId, that.expressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, sumMoney, payStatus, addressStatus, userId, userName, userPhone, userAddress, expressId);
    }
}
