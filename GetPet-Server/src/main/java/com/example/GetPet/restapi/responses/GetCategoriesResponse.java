package com.example.GetPet.restapi.responses;

import com.example.GetPet.database.models.AnimalCategory;

import java.io.Serializable;
import java.util.List;

public class GetCategoriesResponse implements Serializable {

    private List<AnimalCategory> categories;
    private String error = "";

    public GetCategoriesResponse() {
    }

    public GetCategoriesResponse(List<AnimalCategory> categories) {
        this.categories = categories;
    }

    public GetCategoriesResponse(String error) {
        this.error = error;
    }

    public List<AnimalCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<AnimalCategory> categories) {
        this.categories = categories;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
