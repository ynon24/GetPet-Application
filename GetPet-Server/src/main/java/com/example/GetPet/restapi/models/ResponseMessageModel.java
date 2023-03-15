package com.example.GetPet.restapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ResponseMessageModel implements Serializable {

    private int id;
    @JsonProperty("from_id")
    private int senderId;
    @JsonProperty("from_name")
    private String senderName;
    private String subject;
    private String content;

    public ResponseMessageModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
