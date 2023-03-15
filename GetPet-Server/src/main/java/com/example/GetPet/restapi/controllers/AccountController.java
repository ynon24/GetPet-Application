package com.example.GetPet.restapi.controllers;

import com.example.GetPet.database.DatabaseHelper;
import com.example.GetPet.database.exceptions.AccountNotFoundException;
import com.example.GetPet.database.models.Account;
import com.example.GetPet.restapi.requests.LoginRequest;
import com.example.GetPet.restapi.requests.SetAccountFavoritesRequest;
import com.example.GetPet.restapi.responses.BaseResponse;
import com.example.GetPet.restapi.responses.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AccountController {

    private final DatabaseHelper databaseHelper = new DatabaseHelper();


    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Account account;
        try {
            account = databaseHelper.getAccountByEmail(loginRequest.getEmail());
            if (loginRequest.getPassword().equals(account.getPassword())) {
                return new ResponseEntity<>(new LoginResponse(account), HttpStatus.OK);
            }
        } catch (AccountNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return new ResponseEntity<>(new LoginResponse("Login failed: email or password mismatch."), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("signup")
    public ResponseEntity<BaseResponse> createAccount(@RequestBody Account account)  {
        try {
            databaseHelper.addAccount(account);
            return new ResponseEntity<>(new BaseResponse(true, ""),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(false, e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("set-favorites")
    public ResponseEntity<BaseResponse> setAccountFavorites(@RequestBody SetAccountFavoritesRequest request){
        try{
            databaseHelper.setAccountFavorites(request.getAccountId(), request.getFavorites());
            return new ResponseEntity<>(new BaseResponse(true, ""), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new BaseResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
