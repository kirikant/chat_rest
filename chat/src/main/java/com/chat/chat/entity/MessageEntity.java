package com.chat.chat.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private LocalDateTime dateTime;
    private String loginSender;
    private String text;

    @ManyToOne
    private  UserEntity loginReceiver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLoginSender() {
        return loginSender;
    }

    public void setLoginSender(String loginSender) {
        this.loginSender = loginSender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserEntity getLoginReceiver() {
        return loginReceiver;
    }

    public void setLoginReceiver(UserEntity loginReceiver) {
        this.loginReceiver = loginReceiver;
    }

}
