package com.shariful.a1xdeposit;

public class User2 {
    String id,uName,mobile;

    public User2() {
    }

    public User2(String id, String uName, String mobile) {
        this.id = id;
        this.uName = uName;
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
