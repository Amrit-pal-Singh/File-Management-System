package com.example.filemanagement;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityGenerateFileItem extends AppCompatActivity {
    private PlaceHolderRestApi placeHolderRestApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_file_item);

        TextView text = findViewById(R.id.textP);
        if(getIntent().getStringExtra("BarcodeData") != null) {
            String barcode_data = getIntent().getStringExtra("BarcodeData");
            text.setText(barcode_data);

            EditText editText = findViewById(R.id.file_name);
            String file_name = editText.getText().toString();

            // Send data to api
            findViewById(R.id.add_file_btn).setOnClickListener(v -> {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(PlaceHolderRestApi.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                placeHolderRestApi = retrofit.create(PlaceHolderRestApi.class);

                addFile(file_name, barcode_data);
            });
        }
    }

    private void addFile(String file_name, String barcode_data){
        if(file_name.equals("")){
            //Temporary/default name for file
            file_name = "Advitiya Refund";
        }

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", file_name);
        jsonObject.addProperty("qr", barcode_data);

        Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();

        Call<JsonObject> call = placeHolderRestApi.addFile(LoginActivity.TOKEN, jsonObject);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Unsuccessful: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                JsonObject jsonObject = response.body();
                Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

}
