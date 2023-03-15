package com.example.GetPet.restapi.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class SetAccountFavoritesRequest implements Serializable {

    @JsonProperty("account_id")
    private int accountId;
    @JsonProperty("favs")
    private List<Integer> favorites;

    public SetAccountFavoritesRequest() {
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public List<Integer> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Integer> favorites) {
        this.favorites = favorites;
    }
}
