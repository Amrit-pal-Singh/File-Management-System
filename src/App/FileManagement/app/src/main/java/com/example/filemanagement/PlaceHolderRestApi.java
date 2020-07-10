package com.example.filemanagement;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PlaceHolderRestApi {

    String BASE_URL = "http://ce5c83243db6.ngrok.io/";

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

    @POST("api/v1/db/add_file/")
    Call<JsonObject> addFile(@Body JsonObject jsonObject);

    @GET("api/v1/db/roles/")
    Call<JsonObject> getRoles(@Header("Authorization") String userToken);

    @GET("api/v1/db/generated_files/")
    Call<JsonObject> getFiles(@Header("Authorization") String userToken);

}
