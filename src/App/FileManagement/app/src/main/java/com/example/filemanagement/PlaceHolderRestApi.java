package com.example.filemanagement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlaceHolderRestApi {

    @GET("posts")
    Call<List<Post>> getPosts();
}
