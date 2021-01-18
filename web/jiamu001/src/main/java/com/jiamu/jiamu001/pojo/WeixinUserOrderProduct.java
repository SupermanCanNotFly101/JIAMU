package com.jiamu.jiamu001.pojo;

import java.util.Objects;

public class WeixinUserOrderProduct {
    private int id;
    private int oldId;
    private int isAlive;
    private String teaName;
    private double teaPrice;
    private String teaSize;
    private String teaPhotoUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOldId() {
        return oldId;
    }

    public void setOldId(int oldId) {
        this.oldId = oldId;
    }

    public int getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(int isAlive) {
        this.isAlive = isAlive;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public double getTeaPrice() {
        return teaPrice;
    }

    public void setTeaPrice(double teaPrice) {
        this.teaPrice = teaPrice;
    }

    public String getTeaSize() {
        return teaSize;
    }

    public void setTeaSize(String teaSize) {
        this.teaSize = teaSize;
    }

    public String getTeaPhotoUrl() {
        return teaPhotoUrl;
    }

    public void setTeaPhotoUrl(String teaPhotoUrl) {
        this.teaPhotoUrl = teaPhotoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeixinUserOrderProduct that = (WeixinUserOrderProduct) o;
        return id == that.id &&
                oldId == that.oldId &&
                isAlive == that.isAlive &&
                Double.compare(that.teaPrice, teaPrice) == 0 &&
                Objects.equals(teaName, that.teaName) &&
                Objects.equals(teaSize, that.teaSize) &&
                Objects.equals(teaPhotoUrl, that.teaPhotoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, oldId, isAlive, teaName, teaPrice, teaSize, teaPhotoUrl);
    }
}
