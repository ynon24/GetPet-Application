package com.example.GetPet.database.models;

import java.io.Serializable;

public class Message implements Serializable {
    private int id;
    private int sender;
    private int receiver;
    private String content;
    private String subject;

    public Message() {
    }

    public Message(int id, int sender, int receiver, String subject, String content){
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public int getSender() {
        return sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }
}
