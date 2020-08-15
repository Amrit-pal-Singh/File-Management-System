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
    private PlaceHolderRestApi placeHolderRestApi;
    Button b1,b2,b3,b4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_file);
        b1 = (Button) findViewById(R.id.generated_files_by_me);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), activity_view_generated_files_by_me.class);
                startActivity(intent);
            }
        });

        b2 = (Button) findViewById(R.id.app_disapp_files_by_me);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), activity_view_app_disapp_files_by_me.class);
                startActivity(intent);
            }
        });

        b3 = (Button) findViewById(R.id.my_plan_to_send_files);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), activity_view_my_files_plan_to_send.class);
                startActivity(intent);
            }
        });

        b4 = (Button) findViewById(R.id.app_disapp_files_by_others);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), activity_view_files_app_disapp_by_others.class);
                startActivity(intent);
            }
        });




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PlaceHolderRestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        placeHolderRestApi = retrofit.create(PlaceHolderRestApi.class);

        getFilesFun();

    }


    private void getFilesFun(){
        final ListView list = findViewById(R.id.myFilesList);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Demo File 1");

        //Token for Jai: "Token a177092974d276852aa8c638cf6823e0f1a89972"

        Call<JsonObject> call = placeHolderRestApi.getFiles(LoginActivity.TOKEN);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(!response.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "Unsuccessful: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                JsonObject jsonObject = response.body();

                for(Map.Entry<String, JsonElement> entry: jsonObject.entrySet()){
                    JsonObject jsonFile = entry.getValue().getAsJsonObject();
                    StringBuilder file = new StringBuilder();
                    try {
                        file.append(jsonFile.get("qr").toString()).append(" | ");
                        file.append(jsonFile.get("name").toString()).append(" | ");
                        //file.append(jsonFile.get("time_generated").toString()).append(" | ");
                        //file.append(jsonFile.get("restarted").toString()).append(" | ");
                        //file.append(jsonFile.get("path").toString()).append(" | ");
                        //file.append(jsonFile.get("plan_to_send").toString()).append(" | ");
                        // file.append(jsonFile.get("approved").toString()).append(" | ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    arrayList.add(file.toString().replaceAll("\"", ""));
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ActivityViewFile.this,
                            android.R.layout.simple_list_item_1, arrayList);

                    list.setAdapter(arrayAdapter);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String clickedItem=(String) list.getItemAtPosition(position);
                            Toast.makeText(ActivityViewFile.this,clickedItem,Toast.LENGTH_LONG).show();
                        }
                    });

                }

                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}