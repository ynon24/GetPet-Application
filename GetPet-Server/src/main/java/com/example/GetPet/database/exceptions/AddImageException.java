package com.example.GetPet.database.exceptions;

public class AddImageException extends Exception{
    public AddImageException() {
        super("Failed to add image to the database");
    }
}
