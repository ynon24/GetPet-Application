package com.example.GetPet.restapi.responses;

import java.io.Serializable;

public class BaseResponse implements Serializable {

    private boolean isSuccess;
    private String error = "";

    public BaseResponse() {
    }

    public BaseResponse(boolean isSuccess, String error) {
        this.isSuccess = isSuccess;
        this.error = error;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
