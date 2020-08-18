package com.example.filemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReceiveItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_item);

        String barcode_data = getIntent().getStringExtra("BarcodeData");
        if(barcode_data != null) {
            TextView text = findViewById(R.id.textR);
            text.setText(barcode_data);

            // Send data to api
            findViewById(R.id.receive_file_btn).setOnClickListener(v -> {
                receiveFile(barcode_data);
            });
        }
    }

    private void receiveFile(String barcode_data){

        barcode_data = "6549";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("qr", barcode_data);
        jsonObject.addProperty("role", "Instructor");
        jsonObject.addProperty("department", "CSE");
        jsonObject.addProperty("sender_email", "amrit@gmail.com");

        Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();

        Call<JsonObject> call = PlaceHolderRestApi.restApi.receiveFiles(LoginActivity.TOKEN, barcode_data, jsonObject);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Unsuccessful: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                JsonObject jsonObject = response.body();
                Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

}
