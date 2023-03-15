package com.example.GetPet.restapi.controllers;

import com.example.GetPet.database.DatabaseHelper;
import com.example.GetPet.database.models.Message;
import com.example.GetPet.restapi.models.ResponseMessageModel;
import com.example.GetPet.restapi.requests.NewMessageRequest;
import com.example.GetPet.restapi.responses.BaseResponse;
import com.example.GetPet.restapi.responses.GetAccountMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {

    private final DatabaseHelper databaseHelper = new DatabaseHelper();

    @PostMapping("new-message")
    public ResponseEntity<BaseResponse> addMessage(@RequestBody NewMessageRequest request){
        try {
            databaseHelper.addMessage(request.getFrom(), request.getTo(), request.getSubject(), request.getContent());
            return new ResponseEntity<>(new BaseResponse(true, ""), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new BaseResponse(false, e.getMessage()), HttpStatus.OK);
        }
    }

    @DeleteMapping("delete-message")
    public ResponseEntity<BaseResponse> deleteMessage(@RequestParam("message_id") int messageId){
        try {
            databaseHelper.deleteMessage(messageId);
            return new ResponseEntity<>(new BaseResponse(true, ""), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new BaseResponse(false, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("get-messages")
    public ResponseEntity<GetAccountMessageResponse> getAccountMessages(@RequestParam("account_id") int accountId){
        try{
            List<ResponseMessageModel> resultMsgs = new ArrayList<>();
            List<Message> msgs = databaseHelper.getMessagesForAccount(accountId);
            for(Message msg : msgs){
                ResponseMessageModel resultMsg = new ResponseMessageModel();
                String senderName = databaseHelper.getAccountNameById(msg.getSender());
                resultMsg.setId(msg.getId());
                resultMsg.setSenderId(msg.getSender());
                resultMsg.setSenderName(senderName);
                resultMsg.setSubject(msg.getSubject());
                resultMsg.setContent(msg.getContent());
                resultMsgs.add(resultMsg);
            }
            return new ResponseEntity<>(new GetAccountMessageResponse(resultMsgs), HttpStatus.OK);
        } catch (Exception e){
            GetAccountMessageResponse response = new GetAccountMessageResponse();
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
