package com.example.filemanagement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PlaceHolderRestApi {

    /**
     * You can also pass header with dynamic value as below
     *
     * @GET("api/v2.1/search")
     * Call<String> getRestaurantsBySearch(
     * @Query("entity_id") String entity_id,
     * @Query("entity_type") String entity_type,
     * @Query("q") String query,
     * @Header("user-key") String userkey);
     *
     * And
     *
     * Call<String> call = endpoint.getRestaurantsBySearch("3","city","cafes","9900a9720d31dfd5fdb4352700c");
     * */

    //@Headers("Authorization: Token 26eb401a1957223c085e8f62062332e2e35521cd")
    @GET("api/v1/db/generated_files/")
    Call<List<Post>> getPosts(@Header("Authorization") String userKey);

    @POST("api/v1/db/login/")
    Call<Post> createPost(@Body Post post);
}
