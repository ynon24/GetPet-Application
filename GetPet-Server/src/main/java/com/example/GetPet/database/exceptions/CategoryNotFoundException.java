package com.example.GetPet.database.exceptions;

public class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException() {
        super("The category is not exists in the database.");
    }
}
