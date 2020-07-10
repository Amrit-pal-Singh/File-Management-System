package com.example.filemanagement;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static String LOG_TAG = "Main Activity";

    private TextView textViewResult;
    private PlaceHolderRestApi placeHolderRestApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(LOG_TAG, "Entered in OnCreate Method");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById((R.id.text_view_result));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PlaceHolderRestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        placeHolderRestApi = retrofit.create(PlaceHolderRestApi.class);

        //getPosts();
        createPost();
    }


    private void createPost(){
        LoginCredentials loginCredentials = new LoginCredentials("jai69@gmail.com", "new_pass_123");

        Call<LoginCredentials> call = placeHolderRestApi.apiLogin(loginCredentials);

        call.enqueue(new Callback<LoginCredentials>() {
            @Override
            public void onResponse(Call<LoginCredentials> call, Response<LoginCredentials> response) {

                if(!response.isSuccessful()){
                    //textViewResult.setText("Code: " + response.code());
                    //Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginCredentials loginCredentials = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "Token: " + loginCredentials.getToken() + "\n";
                content += "Email: " + loginCredentials.getEmail() + "\n";

                //textViewResult.setText(content);
                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginCredentials> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
