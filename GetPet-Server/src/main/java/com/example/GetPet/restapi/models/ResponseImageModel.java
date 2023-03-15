package com.example.GetPet.restapi.models;

import java.io.Serializable;

public class ResponseImageModel implements Serializable {

    private String uuid;
    private String data;

    public ResponseImageModel() {
    }

    public ResponseImageModel(String uuid, String data) {
        this.uuid = uuid;
        this.data = data;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
