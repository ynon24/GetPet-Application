package com.example.GetPet.restapi.responses;

import com.example.GetPet.restapi.models.ResponsePostModel;

import java.io.Serializable;
import java.util.List;

public class GetPostsResponse implements Serializable {

    private List<ResponsePostModel> posts;
    private String error = "";

    public GetPostsResponse() {
    }

    public GetPostsResponse(String error) {
        this.error = error;
    }

    public GetPostsResponse(List<ResponsePostModel> posts) {
        this.posts = posts;
    }

    public List<ResponsePostModel> getPosts() {
        return posts;
    }

    public void setPosts(List<ResponsePostModel> posts) {
        this.posts = posts;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
