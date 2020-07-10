package com.example.filemanagement;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PlaceHolderRestApi {

    String base_url = "http://192.168.43.154:8000/";

    String token = "";

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
     * */

    @POST("api/v1/db/login/")
    Call<LoginCredentials> apiLogin(@Body LoginCredentials loginCredentials);

    @GET("api/v1/db/roles/")
    Call<JsonObject> getRoles(@Header("Authorization") String userToken);

    @GET("api/v1/db/generated_files/")
    Call<JsonObject> getPosts(@Header("Authorization") String userToken);

}
