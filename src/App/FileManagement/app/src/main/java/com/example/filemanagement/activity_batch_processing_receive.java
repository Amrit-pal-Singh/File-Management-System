package com.example.filemanagement;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_batch_processing_receive extends Activity {

    ArrayList<String> scannedData;
    ArrayList<String> Failures = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_processing_receive);


        if(getIntent().getSerializableExtra("BarcodeData") != null) {
            scannedData = (ArrayList<String>) getIntent().getSerializableExtra("BarcodeData");

            for(String str : scannedData) {
                Failures.add(str.trim()+" | Not Sent");
            }

            ListView listView = findViewById(R.id.listViewReceiveBPActivity);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Failures);
            listView.setAdapter(adapter);

            findViewById(R.id.batch_processing_receive_all_btn).setOnClickListener(v -> {

                for(String qr_data:scannedData){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("qr", qr_data.trim());
                    jsonObject.addProperty("role", LoginActivity.role_fixed);
                    jsonObject.addProperty("department", LoginActivity.department_fixed);
                    jsonObject.addProperty("email", LoginActivity.email_fixed);

                    receiveFile(jsonObject);
                }

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
                    Toast.makeText(getApplicationContext(), entry.getValue().getAsString(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

}
