package com.example.filemanagement;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityPlanToSendItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_to_send_item);

        TextView text = findViewById(R.id.textP);
        if(getIntent().getStringExtra("BarcodeData") != null) {
            text.setText(getIntent().getStringExtra("BarcodeData"));
        }

        ArrayList<String> roles = new ArrayList<>();
        Spinner spinner = findViewById(R.id.spinnerPlanToSend);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
        spinner.setAdapter(spinnerAdapter);
    }
    private class PlanToSendBackgroundTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}