package com.example.filemanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    Spinner roleSpinner;
    String selectedString;
    ArrayList<String> rolesArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_to_send_item);

        //callAPI - GetAllRoles
        getAllRoles();


        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedString = roleSpinner.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TextView text = findViewById(R.id.textP);
        final String qr_data = getIntent().getStringExtra("BarcodeData");
        if(qr_data != null) {

            text.setText(qr_data);
            findViewById(R.id.plan_to_send_btn).setOnClickListener(v -> {

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

        roleSpinner = findViewById(R.id.spinnerPlanToSend);

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