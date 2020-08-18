package com.example.filemanagement;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPlanToSendItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_to_send_item);

        TextView text = findViewById(R.id.textP);
        final String qr_data = getIntent().getStringExtra("BarcodeData");
        if(qr_data != null) {
            text.setText(qr_data);

            findViewById(R.id.plan_to_send_btn).setOnClickListener(v -> {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("qr", "6549");
                jsonObject.addProperty("role", "Instructor");
                jsonObject.addProperty("department", "CSE");
                jsonObject.addProperty("sender_email", "amrit@gmail.com");

                planToSend(jsonObject);
            });

        }

        ArrayList<String> roles = new ArrayList<>();
        Spinner spinner = findViewById(R.id.spinnerPlanToSend);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                roles);
        spinner.setAdapter(spinnerAdapter);
    }

    private void planToSend(JsonObject jsonObject){
//        barcode_data = "6549";

        Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();

        Call<JsonObject> call = PlaceHolderRestApi.restApi.planToSend(
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

                for(Map.Entry<String, JsonElement> entry: jsonObject.entrySet()){
                    Toast.makeText(getApplicationContext(), entry.getValue().toString(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


}