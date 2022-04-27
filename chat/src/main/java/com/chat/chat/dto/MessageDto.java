package com.chat.chat.dto;

import java.time.LocalDateTime;

public class MessageDto {

    private long id;
    private LocalDateTime dateTime;
    private String loginSender;
    private String text;
    private String receiverLogin;


    public MessageDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getLoginSender() {
        return loginSender;
    }

    public String getText() {
        return text;
    }

    public String getReceiverLogin() {
        return receiverLogin;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setLoginSender(String loginSender) {
        this.loginSender = loginSender;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setReceiverLogin(String receiverLogin) {
        this.receiverLogin = receiverLogin;
    }

    @Override
    public String toString() {
        return "Message from " + loginSender +" : "+ text +"  Message was sent:"+dateTime;
    }
}
