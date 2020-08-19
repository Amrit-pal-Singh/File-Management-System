package com.example.filemanagement;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_batch_processing_plan_to_send extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_processing_plan_to_send);

        final ArrayList<String> scannedData;

        if(getIntent().getSerializableExtra("BarcodeData") != null) {
            scannedData = (ArrayList<String>) getIntent().getSerializableExtra("BarcodeData");

            ListView listView = findViewById(R.id.listViewPlanToSendBPActivity);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scannedData);
            listView.setAdapter(adapter);

            findViewById(R.id.batch_processing_plan_to_send_btn).setOnClickListener(v -> {
                
                for(String qr_data:scannedData){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("qr", qr_data);
                    jsonObject.addProperty("role", "Instructor");
                    jsonObject.addProperty("department", "CSE");
                    jsonObject.addProperty("email", "amrit@gmail.com");

                    planToSend(jsonObject);

                }

            });

        }

        ArrayList<String> roles = new ArrayList<>();
        Spinner spinner = findViewById(R.id.spinnerPlanToSendBP);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
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
