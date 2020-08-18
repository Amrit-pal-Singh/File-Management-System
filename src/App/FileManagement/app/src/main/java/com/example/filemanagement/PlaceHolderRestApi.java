package com.example.filemanagement;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PlaceHolderRestApi {

    String BASE_URL = "http://192.168.1.31:8000/";

    String token = "";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    PlaceHolderRestApi restApi = retrofit.create(PlaceHolderRestApi.class);

    /**
     * You can also pass header with dynamic value as below
     *
     * @GET("api/v2.1/search")
     * Call<String> getRestaurantsBySearch(
     * @Query("entity_id") String entity_id,
     * @Query("entity_type") String entity_type,
     * @Header("user-key") String userKey);
     *
     * */

    @POST("api/v1/db/login/")
    Call<LoginCredentials> apiLogin(@Body LoginCredentials loginCredentials);

    @GET("api/v1/db/roles/")
    Call<JsonObject> getRoles(@Header("Authorization") String userToken);

    @POST("api/v1/db/add_file/")
    Call<JsonObject> addFile(@Header("Authorization") String userToken, @Body JsonObject jsonObject);

    /** List Files */
    @GET("api/v1/db/generated_files/")
    Call<JsonObject> getFiles(@Header("Authorization") String userToken);

    /** Receive File */
    @PUT("api/v1/db/receive_file/{qr}/")
    Call<JsonObject> receiveFile(@Header("Authorization") String userToken,
                                  @Path("qr") String qr_code,
                                  @Body JsonObject jsonObject);

    /** PlanToSend */
    @PUT("api/v1/db/plan_to_send/{qr}/")
    Call<JsonObject> planToSend(@Header("Authorization") String userToken,
                                @Path("qr") String qr_code,
                                @Body JsonObject jsonObject);

    /** ApproveFile */
    @PUT("api/v1/db/approve_disapprove_file/{qr}/")
    Call<JsonObject> approveFile(@Header("Authorization") String userToken,
                                 @Path("qr") String qr_code,
                                 @Body JsonObject jsonObject);

    /** view_my_plan_to_send_files */
    @GET("api/v1/db/view_my_plan_to_send_files/")
    Call<JsonObject> viewPlanToSendFiles(@Header("Authorization") String userToken);

    /** file_detail */
    @GET("api/v1/db/file_detail/{qr}/")
    Call<JsonObject> getFileDetails(@Header("Authorization") String userToken,
                                    @Path("qr") String qr_code);

    /** view_my_approved_disapproved_files */
    @GET("api/v1/db/view_my_approved_disaaproved_files/")
    Call<JsonObject> viewApproveDisapprovedFiles(@Header("Authorization") String userToken);


}
