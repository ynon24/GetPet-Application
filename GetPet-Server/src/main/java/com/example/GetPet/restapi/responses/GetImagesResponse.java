package com.example.GetPet.restapi.responses;

import com.example.GetPet.restapi.models.ResponseImageModel;

import java.io.Serializable;
import java.util.List;

public class GetImagesResponse implements Serializable {

    private List<ResponseImageModel> images;
    private String error = "";

    public GetImagesResponse() {
    }

    public GetImagesResponse(List<ResponseImageModel> images) {
        this.images = images;
    }

    public GetImagesResponse(String error) {
        this.error = error;
    }

    public List<ResponseImageModel> getImages() {
        return images;
    }

    public void setImages(List<ResponseImageModel> images) {
        this.images = images;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
