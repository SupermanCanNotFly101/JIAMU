package com.jiamu.jiamu001.pojo;

import java.util.Objects;

public class TeaProduct {
    private int id;
    private String teaName;
    private double teaPrice;
    private String teaSize;
    private String teaTaste;
    private String teaInfo;
    private String teaPhotoUrl;
    private String teaPhotoUrlList;
    private int teaType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTeaTaste() {
        return teaTaste;
    }

    public void setTeaTaste(String teaTaste) {
        this.teaTaste = teaTaste;
    }

    public String getTeaInfo() {
        return teaInfo;
    }

    public void setTeaInfo(String teaInfo) {
        this.teaInfo = teaInfo;
    }

    public String getTeaPhotoUrl() {
        return teaPhotoUrl;
    }

    public void setTeaPhotoUrl(String teaPhotoUrl) {
        this.teaPhotoUrl = teaPhotoUrl;
    }

    public String getTeaPhotoUrlList() {
        return teaPhotoUrlList;
    }

    public void setTeaPhotoUrlList(String teaPhotoUrlList) {
        this.teaPhotoUrlList = teaPhotoUrlList;
    }

    public int getTeaType() {
        return teaType;
    }

    public void setTeaType(int teaType) {
        this.teaType = teaType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeaProduct that = (TeaProduct) o;
        return id == that.id &&
                Double.compare(that.teaPrice, teaPrice) == 0 &&
                teaType == that.teaType &&
                Objects.equals(teaName, that.teaName) &&
                Objects.equals(teaSize, that.teaSize) &&
                Objects.equals(teaTaste, that.teaTaste) &&
                Objects.equals(teaInfo, that.teaInfo) &&
                Objects.equals(teaPhotoUrl, that.teaPhotoUrl) &&
                Objects.equals(teaPhotoUrlList, that.teaPhotoUrlList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teaName, teaPrice, teaSize, teaTaste, teaInfo, teaPhotoUrl, teaPhotoUrlList, teaType);
    }
}
