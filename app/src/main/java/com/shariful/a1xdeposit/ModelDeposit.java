package com.shariful.a1xdeposit;

public class ModelDeposit {

    String myUid,pId, amount, currency_type, payment_method, phone, transactionId, userId, status;

    public ModelDeposit() {
    }

    public ModelDeposit(String myUid, String pId, String amount, String currency_type, String payment_method, String phone, String transactionId, String userId, String status) {
        this.myUid = myUid;
        this.pId = pId;
        this.amount = amount;
        this.currency_type = currency_type;
        this.payment_method = payment_method;
        this.phone = phone;
        this.transactionId = transactionId;
        this.userId = userId;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency_type() {
        return currency_type;
    }

    public void setCurrency_type(String currency_type) {
        this.currency_type = currency_type;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}