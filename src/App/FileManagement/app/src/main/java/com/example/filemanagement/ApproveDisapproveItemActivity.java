package com.example.filemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveDisapproveItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_disapprove_item);

        TextView text = findViewById(R.id.textAD);

        final String qr_data = getIntent().getStringExtra("BarcodeData");
        if(qr_data != null) {
            text.setText(qr_data);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("qr", qr_data.trim());
            jsonObject.addProperty("email", LoginCredentials.getEmail());
            jsonObject.addProperty("role", "Director");
            jsonObject.addProperty("department", "");


            findViewById(R.id.approve_btn).setOnClickListener(v -> {

                jsonObject.addProperty("approve", 1);

                approveFile(jsonObject);

            });

            findViewById(R.id.disapprove_btn).setOnClickListener(v -> {

                jsonObject.addProperty("approve", 0);

            });

        }
    }

    private void approveFile(JsonObject jsonObject){
        Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();

        Call<JsonObject> call = PlaceHolderRestApi.restApi.approveFile(
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
                Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();

//                for(Map.Entry<String, JsonElement> entry: jsonObject.entrySet()){
//                    Toast.makeText(getApplicationContext(), entry.getValue().getAsString(), Toast.LENGTH_LONG).show();
//                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

}
