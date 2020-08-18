package com.example.filemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityViewFile extends AppCompatActivity {
    Button b1, b2, b3, b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_file);
        b1 = findViewById(R.id.generated_files_by_me);
        b1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), activity_view_generated_files_by_me.class);
            startActivity(intent);
        });

        b2 = findViewById(R.id.app_disapp_files_by_me);
        b2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), activity_view_app_disapp_files_by_me.class);
            startActivity(intent);
        });

        b3 = findViewById(R.id.my_plan_to_send_files);
        b3.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), activity_view_my_files_plan_to_send.class);
            startActivity(intent);
        });

        b4 = findViewById(R.id.app_disapp_files_by_others);
        b4.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), activity_view_files_app_disapp_by_others.class);
            startActivity(intent);
        });


        getFilesFun();

    }


    private void getFilesFun() {
        final ListView list = findViewById(R.id.myFilesList);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Demo File 1");

        Call<JsonObject> call = PlaceHolderRestApi.restApi.getFiles(LoginActivity.TOKEN);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "Unsuccessful: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                JsonObject jsonObject = response.body();

                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    JsonObject jsonFile = entry.getValue().getAsJsonObject();
                    StringBuilder file = new StringBuilder();
                    try {
                        file.append(jsonFile.get("qr").getAsString()).append(" | ");
                        file.append(jsonFile.get("name").getAsString());
                        //file.append(jsonFile.get("time_generated").getAsString()).append(" | ");
                        //file.append(jsonFile.get("restarted").getAsString()).append(" | ");
                        //file.append(jsonFile.get("path").getAsString()).append(" | ");
                        //file.append(jsonFile.get("plan_to_send").getAsString()).append(" | ");
                        // file.append(jsonFile.get("approved").getAsString()).append(" | ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    arrayList.add(file.toString());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ActivityViewFile.this,
                        android.R.layout.simple_list_item_1, arrayList);

                list.setAdapter(arrayAdapter);

                list.setOnItemClickListener((parent, view, position, id) -> {
                    String clickedItem = (String) list.getItemAtPosition(position);
                    String[] strList = clickedItem.split("\\|");
                    String intentData = strList[0].trim();

                    startActivity(new Intent(getApplicationContext(),
                            activity_view_details_of_a_file.class)
                            .putExtra("qr_data", intentData));
                    //Toast.makeText(ActivityViewFile.this,clickedItem,Toast.LENGTH_LONG).show();
                });

                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}