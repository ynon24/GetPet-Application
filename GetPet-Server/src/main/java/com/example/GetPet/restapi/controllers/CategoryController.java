package com.example.GetPet.restapi.controllers;

import com.example.GetPet.database.DatabaseHelper;
import com.example.GetPet.restapi.responses.GetCategoriesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    private final DatabaseHelper databaseHelper = new DatabaseHelper();

    @GetMapping("get-categories")
    public ResponseEntity<GetCategoriesResponse> getCategories(){
        try {
            return new ResponseEntity<>(new GetCategoriesResponse(databaseHelper.getAllCategories()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new GetCategoriesResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
