package com.jiamu.jiamu001.pojo;

import java.sql.Timestamp;
import java.util.Objects;

public class WeixinUser {
    private int id;
    private String openId;
    private Timestamp createdTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeixinUser that = (WeixinUser) o;
        return id == that.id &&
                Objects.equals(openId, that.openId) &&
                Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openId, createdTime);
    }
}
