package com.example.GetPet.database.exceptions;

public class AccountNotFoundException extends Exception{

    public AccountNotFoundException(){
        super("The account is not exists in the database.");
    }
}
