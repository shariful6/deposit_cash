package com.shariful.a1xdeposit;

public class Chat {

    String message,receiver,sender,uName,pId;

    public Chat() {
    }

    public Chat(String message, String receiver, String sender, String uName, String pId) {
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
        this.uName = uName;
        this.pId = pId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
