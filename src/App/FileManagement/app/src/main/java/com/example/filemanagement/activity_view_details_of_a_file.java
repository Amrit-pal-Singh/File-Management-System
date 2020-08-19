package com.example.filemanagement;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_view_details_of_a_file extends AppCompatActivity {
    private PlaceHolderRestApi placeHolderRestApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details_of_a_file);

        String qr_data = getIntent().getStringExtra("qr_data");

        getFileDetailsFun(qr_data);
    }

    private void getFileDetailsFun(String qr_data) {

        Call<JsonObject> call = PlaceHolderRestApi.restApi.getFileDetails(LoginActivity.TOKEN, qr_data);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "Unsuccessful: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                JsonObject jsonObject = response.body();

                TextView tvQR = findViewById(R.id.qr);
                tvQR.setText(jsonObject.get("qr").getAsString());

                TextView tvName = findViewById(R.id.name);
                tvName.setText(jsonObject.get("name").getAsString());

                TextView tvUser = findViewById(R.id.user);
                tvUser.setText(jsonObject.get("user").getAsString());

                TextView tvTime = findViewById(R.id.time_generated);
                String arr[] = jsonObject.get("time_generated").getAsString().split("\\.");
                tvTime.setText(arr[0]);

                TextView tvRestarted = findViewById(R.id.restarted);
                tvRestarted.setText(jsonObject.get("restarted").getAsString());

                TextView tvPath = findViewById(R.id.path);
                tvPath.setText(jsonObject.get("path").getAsString()
                        .replaceAll("#", "->"));

                TextView tvPtS = findViewById(R.id.plan_to_send);
                tvPtS.setText(jsonObject.get("plan_to_send").getAsString());

                TextView tvPtSG = findViewById(R.id.plan_to_send_generator);
                tvPtSG.setText(jsonObject.get("plan_to_send_generator").getAsString());

                TextView tvApp = findViewById(R.id.approved);
                tvApp.setText(jsonObject.get("approved").getAsString()
                        .replaceAll("#", "->"));

                TextView tvDisApp = findViewById(R.id.disapproved);
                tvDisApp.setText(jsonObject.get("disapproved").getAsString().
                        replaceAll("#", "->"));


                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}