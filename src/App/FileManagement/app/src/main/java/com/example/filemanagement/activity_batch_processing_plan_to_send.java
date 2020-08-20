package com.example.filemanagement;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    Spinner roleSpinner;
    String selectedString;
    ArrayList<String> rolesArray = new ArrayList<>();

    ArrayList<String> Failures = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_processing_plan_to_send);


        //callAPI - GetAllRoles
        getAllRoles();
        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();

        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedString = roleSpinner.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayList<String> scannedData;

        if(getIntent().getSerializableExtra("BarcodeData") != null) {

            scannedData = (ArrayList<String>) getIntent().getSerializableExtra("BarcodeData");

            for(String str : scannedData) {
                Failures.add(str.trim() + " | Not Sent");
            }

            ListView listView = findViewById(R.id.listViewPlanToSendBPActivity);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Failures);
            listView.setAdapter(adapter);

            findViewById(R.id.batch_processing_plan_to_send_btn).setOnClickListener(v -> {
                
                for(String qr_data:scannedData){

                    if(!rolesArray.isEmpty() && !selectedString.isEmpty()){

                        String[] str_arr = selectedString.split("\\|");

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("qr", qr_data.trim());
                        jsonObject.addProperty("role", str_arr[0].trim());
                        jsonObject.addProperty("department", str_arr[1].trim());
                        jsonObject.addProperty("sender_email", LoginActivity.email_fixed);

                        planToSend(jsonObject);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please select role", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void planToSend(JsonObject jsonObject){

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
                String qr = jsonObject.get("qr").getAsString().trim();
                String qr1 = qr+" | Not Sent";
                for(int i=0; i<Failures.size(); i++) {
                    if(Failures.get(i).equals(qr1)) {
                        Failures.set(i, qr+" | Successfully Sent");
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }

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


    private void getAllRoles(){

        roleSpinner = findViewById(R.id.spinnerPlanToSendBP);

        Call<JsonObject> call = PlaceHolderRestApi.restApi.getAllRoles(LoginActivity.TOKEN);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Unsuccessful: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                JsonObject jsonObject = response.body();

                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    JsonObject roleObject = entry.getValue().getAsJsonObject();
                    StringBuilder rolesBuilder = new StringBuilder();
                    try {
                        rolesBuilder.append(roleObject.get("name").getAsString()).append(" | ");
                        rolesBuilder.append(roleObject.get("department").getAsString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    rolesArray.add(rolesBuilder.toString());
                }

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),
                        android.R.layout.simple_spinner_dropdown_item,
                        rolesArray);

                roleSpinner.setAdapter(spinnerAdapter);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }


}
