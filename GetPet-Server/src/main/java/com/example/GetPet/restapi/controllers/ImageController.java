package com.example.GetPet.restapi.controllers;

import com.example.GetPet.database.DatabaseHelper;
import com.example.GetPet.restapi.models.ResponseImageModel;
import com.example.GetPet.restapi.responses.GetImagesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ImageController {

    private final DatabaseHelper databaseHelper = new DatabaseHelper();

    @GetMapping("get-images")
    public ResponseEntity<GetImagesResponse> getImages(@RequestParam List<String> uuids){
        try{
            List<ResponseImageModel> resultImages = new ArrayList<>();
            for (String uuid : uuids){
                resultImages.add(new ResponseImageModel(uuid, databaseHelper.getImage(uuid)));
            }
            return new ResponseEntity<>(new GetImagesResponse(resultImages), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new GetImagesResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
