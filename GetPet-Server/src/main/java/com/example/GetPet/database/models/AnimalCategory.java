package com.example.GetPet.database.models;

import java.io.Serializable;

public class AnimalCategory implements Serializable {
    private int id;
    private String name;

    public AnimalCategory() {
    }

    public AnimalCategory(int id, String name){
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
