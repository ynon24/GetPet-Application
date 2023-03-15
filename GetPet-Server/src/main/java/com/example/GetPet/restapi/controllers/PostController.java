package com.example.GetPet.restapi.controllers;

import com.example.GetPet.database.DatabaseHelper;
import com.example.GetPet.database.models.Account;
import com.example.GetPet.database.models.Post;
import com.example.GetPet.restapi.models.ResponsePostModel;
import com.example.GetPet.restapi.requests.NewPostRequest;
import com.example.GetPet.restapi.responses.BaseResponse;
import com.example.GetPet.restapi.responses.GetAccountPostsResponse;
import com.example.GetPet.restapi.responses.GetPostsIdsResponse;
import com.example.GetPet.restapi.responses.GetPostsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {

    private final DatabaseHelper databaseHelper = new DatabaseHelper();

    @DeleteMapping("delete-post")
    public ResponseEntity<BaseResponse> deletePost(@RequestParam("post_id") int postId){
        try {
            databaseHelper.deletePost(postId);
            return new ResponseEntity<>(new BaseResponse(true, ""), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new BaseResponse(false, e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("add-post")
    public ResponseEntity<BaseResponse> addPost(@RequestBody NewPostRequest request){
        try {
            List<String> uuids = new ArrayList<>();
            for(String data : request.getImages()){
                uuids.add(databaseHelper.addImage(data));
            }
            databaseHelper.addPost(request.getOwnerId(), request.getName(), request.getCategoryId(),
                    request.getShortDesc(), request.getLongDesc(), uuids);
            return new ResponseEntity<>(new BaseResponse(true, ""), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new BaseResponse(false, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("get-account-posts")
    public ResponseEntity<GetAccountPostsResponse> getAccountPosts(@RequestParam("account_id") int accountId,
                                                                   @RequestParam("category_id") int categoryId){
        try{
            List<ResponsePostModel> resultPosts = new ArrayList<>();
            List<Post> dbPosts;
            if(categoryId == 0){
                dbPosts = databaseHelper.getAccountPosts(accountId);
            } else {
                dbPosts = databaseHelper.getAccountPostsByCategory(accountId, categoryId);
            }
            for(Post post : dbPosts){
                ResponsePostModel resultPost = new ResponsePostModel();
                Account owner = databaseHelper.getAccountById(post.getOwner());
                resultPost.setCategory(databaseHelper.getAnimalCategoryById(post.getCategory()).getName());
                resultPost.setId(post.getId());
                resultPost.setName(post.getName());
                resultPost.setShortDescription(post.getShortDescription());
                resultPost.setLongDescription(post.getLongDescription());
                resultPost.setOwnerId(post.getOwner());
                resultPost.setOwnerName(owner.getLastName() + " " + owner.getFirstName());
                resultPost.setEmail(owner.getEmail());
                resultPost.setPhoneNum(owner.getPhoneNumber());
                resultPost.setImages(post.getImages());
                resultPosts.add(resultPost);
            }
            GetAccountPostsResponse response = new GetAccountPostsResponse();
            response.setPosts(resultPosts);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            GetAccountPostsResponse response = new GetAccountPostsResponse();
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get-posts-ids")
    public ResponseEntity<GetPostsIdsResponse> getPostsIds(@RequestParam("category_id") int categoryId){
        GetPostsIdsResponse response = new GetPostsIdsResponse();
        if (categoryId == 0){
            response.setIds(databaseHelper.getAllPostsIds());
        } else {
            response.setIds(databaseHelper.getPostsIdsByCategory(categoryId));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get-posts-by-ids")
    public ResponseEntity<GetPostsResponse> getPosts(@RequestParam("ids_list") ArrayList<Integer> idsList){
        if(idsList.isEmpty()){
            return new ResponseEntity<>(new GetPostsResponse("cannot fetch empty list"), HttpStatus.BAD_REQUEST);
        }
        try {
            List<ResponsePostModel> resultPosts = new ArrayList<>();
            List<Post> dbPosts = databaseHelper.getPostsByIds(idsList);
            for (Post post : dbPosts) {
                ResponsePostModel resultPost = new ResponsePostModel();
                Account owner = databaseHelper.getAccountById(post.getOwner());
                resultPost.setCategory(databaseHelper.getAnimalCategoryById(post.getCategory()).getName());
                resultPost.setId(post.getId());
                resultPost.setName(post.getName());
                resultPost.setShortDescription(post.getShortDescription());
                resultPost.setLongDescription(post.getLongDescription());
                resultPost.setOwnerId(post.getOwner());
                resultPost.setOwnerName(owner.getLastName() + " " + owner.getFirstName());
                resultPost.setEmail(owner.getEmail());
                resultPost.setPhoneNum(owner.getPhoneNumber());
                resultPost.setImages(post.getImages());
                resultPosts.add(resultPost);
            }
            return new ResponseEntity<>(new GetPostsResponse(resultPosts), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new GetPostsResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
