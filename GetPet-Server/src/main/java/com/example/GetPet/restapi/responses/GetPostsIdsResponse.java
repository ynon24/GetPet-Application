package com.example.GetPet.restapi.responses;

import java.io.Serializable;
import java.util.List;

public class GetPostsIdsResponse implements Serializable {

    List<Integer> ids;

    public GetPostsIdsResponse() {
    }

    public GetPostsIdsResponse(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
