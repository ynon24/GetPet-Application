package com.example.GetPet.database.exceptions;

public class ImageNotFoundException extends Exception{
    public ImageNotFoundException() {
        super("The image's uuid is not exists in the database.");
    }
}
