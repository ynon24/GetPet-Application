package com.example.GetPet.database.models;

import java.io.Serializable;
import java.util.List;

public class Post implements Serializable {
    private int id;
    private int owner;
    private String name;
    private int category;
    private String shortDescription;
    private String longDescription;
    private List<String> images;

    public Post() {
    }

    public Post(int id, int owner, String name, int category, String shortDescription, String longDescription,
                List<String> images){
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.category = category;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.images = images;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCategory() {
        return category;
    }

    public int getOwner() {
        return owner;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getName() {
        return name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}

