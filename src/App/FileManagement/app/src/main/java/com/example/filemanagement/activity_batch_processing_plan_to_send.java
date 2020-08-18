package com.example.filemanagement;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class activity_batch_processing_plan_to_send extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_processing_plan_to_send);

        ArrayList<String> scannedData = new ArrayList<>();

        if(getIntent().getSerializableExtra("BarcodeData") != null) {
            scannedData = (ArrayList<String>) getIntent().getSerializableExtra("BarcodeData");
        }

        ListView listView = findViewById(R.id.listViewPlanToSendBPActivity);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scannedData);
        listView.setAdapter(adapter);

        ArrayList<String> roles = new ArrayList<>();
        Spinner spinner = findViewById(R.id.spinnerPlanToSendBP);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
        spinner.setAdapter(spinnerAdapter);
    }

}
