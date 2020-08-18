package com.example.filemanagement;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

public class activity_view_details_of_a_file extends AppCompatActivity {
    private PlaceHolderRestApi placeHolderRestApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details_of_a_file);

        String QrValue = getIntent().getStringExtra("QrValue");

        getFileDetailsFun();
    }

    private void getFileDetailsFun(){
        //Token for Jai: "Token a177092974d276852aa8c638cf6823e0f1a89972"

        Call<JsonObject> call = PlaceHolderRestApi.restApi.getFileDetails(LoginActivity.TOKEN);

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
                    String qr="", name="", user="", time="", restarted="", path="", plan_to_send="", plan_to_send_gen="", app="", disapp="";
                    try {
                        qr += jsonFile.get("qr").toString();
                        name += jsonFile.get("name").toString();
                        user += jsonFile.get("user").toString();
                        time += jsonFile.get("time_generated").toString();
                        restarted += jsonFile.get("restarted").toString();
                        path += jsonFile.get("path").toString();
                        plan_to_send += jsonFile.get("plan_to_send").toString();
                        plan_to_send_gen += jsonFile.get("plan_to_send_generator").toString();
                        app += jsonFile.get("approved").toString();
                        disapp += jsonFile.get("disapproved").toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    path.toString().replaceAll("#", "->");
                    app.toString().replaceAll("#", "->");
                    disapp.toString().replaceAll("#", "->");

                    TextView tvQR = (TextView) findViewById(R.id.qr);
                    tvQR.setText(qr);

                    TextView tvName = (TextView) findViewById(R.id.name);
                    tvName.setText(name);

                    TextView tvUser = (TextView) findViewById(R.id.user);
                    tvUser.setText(user);

                    TextView tvTime = (TextView) findViewById(R.id.time_generated);
                    tvTime.setText(time);

                    TextView tvRestarted = (TextView) findViewById(R.id.restarted);
                    tvRestarted.setText(restarted);

                    TextView tvPath = (TextView) findViewById(R.id.path);
                    tvPath.setText(path);

                    TextView tvPtS = (TextView) findViewById(R.id.plan_to_send);
                    tvPtS.setText(plan_to_send);

                    TextView tvPtSG = (TextView) findViewById(R.id.plan_to_send_generator);
                    tvPtSG.setText(plan_to_send_gen);

                    TextView tvApp = (TextView) findViewById(R.id.approved);
                    tvApp.setText(app);

                    TextView tvDisApp = (TextView) findViewById(R.id.disapproved);
                    tvDisApp.setText(disapp);

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