package com.example.filemanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_file);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PlaceHolderRestApi.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        placeHolderRestApi = retrofit.create(PlaceHolderRestApi.class);

        getPosts();

    }


    private void getPosts(){
        final ListView list = findViewById(R.id.myFilesList);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Demo File 1");

        //Token for Jai: "Token a177092974d276852aa8c638cf6823e0f1a89972"

        Call<JsonObject> call = placeHolderRestApi.getPosts(LoginActivity.TOKEN);

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
                        file.append(jsonFile.get("time_generated").toString()).append(" | ");
                        file.append(jsonFile.get("restarted").toString()).append(" | ");
                        file.append(jsonFile.get("path").toString()).append(" | ");
                        file.append(jsonFile.get("plan_to_send").toString()).append(" | ");
                        file.append(jsonFile.get("approved").toString()).append(" | ");
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