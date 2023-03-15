package com.example.GetPet.restapi.responses;

import com.example.GetPet.restapi.models.ResponseMessageModel;

import java.io.Serializable;
import java.util.List;

public class GetAccountMessageResponse implements Serializable {

    private List<ResponseMessageModel> messages;
    private String error = "";

    public GetAccountMessageResponse() {
    }

    public GetAccountMessageResponse(List<ResponseMessageModel> message) {
        this.messages = message;
    }

    public List<ResponseMessageModel> getMessages() {
        return messages;
    }

    public void setMessages(List<ResponseMessageModel> messages) {
        this.messages = messages;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
