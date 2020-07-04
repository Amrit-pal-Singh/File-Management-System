package com.example.filemanagement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PlaceHolderRestApi {

    @GET("posts")
    Call<List<Post>> getPosts();

    @POST("api/v1/db/login/")
    Call<Post> createPost(@Body Post post);
}
