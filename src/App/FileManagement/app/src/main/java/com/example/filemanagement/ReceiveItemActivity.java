package com.example.filemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReceiveItemActivity extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_item);

        ReceiveItemActivity.context = getApplicationContext();

        final String qr_data = getIntent().getStringExtra("BarcodeData");
        if(qr_data != null) {
            TextView text = findViewById(R.id.textR);
            text.setText(qr_data);

            findViewById(R.id.receive_file_btn).setOnClickListener(v -> {

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("qr", qr_data.trim());
                jsonObject.addProperty("role", LoginActivity.role_fixed);
                jsonObject.addProperty("department", LoginActivity.department_fixed);
                jsonObject.addProperty("email", LoginActivity.email_fixed);

                // Send data to api
                receiveFile(jsonObject);
            });
        }
    }

    private void receiveFile(JsonObject jsonObject){

        Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();

        Call<JsonObject> call = PlaceHolderRestApi.restApi.receiveFile(
                LoginActivity.TOKEN,
                jsonObject.get("qr").getAsString(),
                jsonObject);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Unsuccessful: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                JsonObject jsonObject = response.body();
                Toast.makeText(context, jsonObject.toString(), Toast.LENGTH_LONG).show();

                for(Map.Entry<String, JsonElement> entry: jsonObject.entrySet()){
                    Toast.makeText(context, entry.getValue().getAsString(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

}
