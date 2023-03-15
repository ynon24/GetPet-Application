package com.example.GetPet.restapi.responses;

import com.example.GetPet.database.models.Account;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class LoginResponse implements Serializable {
    private int id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("phone_num")
    private String phoneNumber;
    @JsonProperty("favs")
    private List<Integer> favorites;
    private String error = "";

    public LoginResponse(){
    }

    public LoginResponse(Account account) {
        this.id = account.getId();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.phoneNumber = account.getPhoneNumber();
        this.favorites = account.getFavorites();
    }

    public LoginResponse(String error) {
        this.error = error;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Integer> getFavorites() {
        return favorites;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}


