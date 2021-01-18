package com.jiamu.jiamu001.pojo;

import java.util.Objects;

public class WeixinUserAddress {
    private int id;
    private int userId;
    private String userName;
    private String userPhone;
    private String userAddress1a;
    private String userAddress1b;
    private String userAddress1c;

    public String getUserAddress1a() {
        return userAddress1a;
    }

    public void setUserAddress1a(String userAddress1a) {
        this.userAddress1a = userAddress1a;
    }

    public String getUserAddress1b() {
        return userAddress1b;
    }

    public void setUserAddress1b(String userAddress1b) {
        this.userAddress1b = userAddress1b;
    }

    public String getUserAddress1c() {
        return userAddress1c;
    }

    public void setUserAddress1c(String userAddress1c) {
        this.userAddress1c = userAddress1c;
    }

    private String userAddress2;

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




    public String getUserAddress2() {
        return userAddress2;
    }

    public void setUserAddress2(String userAddress2) {
        this.userAddress2 = userAddress2;
    }


}
