package com.shariful.a1xdeposit.Client;

public class ClientModelWithdraw {

    String myUid,pId,userId,code,ammount,payment_method,currency_type,phone,status;

    public ClientModelWithdraw() {
    }

    public ClientModelWithdraw(String myUid, String pId, String userId, String code, String ammount, String payment_method, String currency_type, String phone, String status) {
        this.myUid = myUid;
        this.pId = pId;
        this.userId = userId;
        this.code = code;
        this.ammount = ammount;
        this.payment_method = payment_method;
        this.currency_type = currency_type;
        this.phone = phone;
        this.status = status;
    }

    public String getMyUid() {
        return myUid;
    }

    public void setMyUid(String myUid) {
        this.myUid = myUid;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getCurrency_type() {
        return currency_type;
    }

    public void setCurrency_type(String currency_type) {
        this.currency_type = currency_type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
