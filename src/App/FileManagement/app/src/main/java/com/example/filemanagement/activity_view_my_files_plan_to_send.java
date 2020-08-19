package com.example.filemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class activity_view_my_files_plan_to_send extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_files_plan_to_send);

        viewMyPlantToSendFiles();

    }

    private void viewMyPlantToSendFiles(){

        final ListView list = findViewById(R.id.my_files_plan_to_send_list);

        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        arrayList.add("Demo File 1");

        Call<JsonObject> call = PlaceHolderRestApi.restApi.viewPlanToSendFiles(LoginActivity.TOKEN);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "Unsuccessful: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                JsonObject jsonObject = response.body();
                String last_plan_to_send = "";
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    JsonObject jsonFile = entry.getValue().getAsJsonObject();
                    StringBuilder file = new StringBuilder();
                    try {
                        file.append(jsonFile.get("qr").getAsString()).append(" | ");
                        file.append(jsonFile.get("name").getAsString());
                        last_plan_to_send = jsonFile.get("last_plan_to_send_time").getAsString();
                        dateList.add(last_plan_to_send);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    arrayList.add(file.toString());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, arrayList);

//                list.setAdapter(arrayAdapter);
                CustomListAdapter listAdapter = new CustomListAdapter(activity_view_my_files_plan_to_send.this , R.layout.custom_list_item , arrayList, dateList);
                list.setAdapter(listAdapter);

                list.setOnItemClickListener((parent, view, position, id) -> {
                    String clickedItem = (String) list.getItemAtPosition(position);
                    String[] strList = clickedItem.split("\\|");
                    String intentData = strList[0].trim();

                    startActivity(new Intent(getApplicationContext(),
                            activity_view_details_of_a_file.class)
                            .putExtra("qr_data", intentData));
                    //Toast.makeText(ActivityViewFile.this,clickedItem,Toast.LENGTH_LONG).show();
                });

                Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}