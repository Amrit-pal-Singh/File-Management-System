package com.example.filemanagement;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class activity_batch_processing_plan_to_send extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_processing_plan_to_send);

        TextView text = findViewById(R.id.lst_scan);
        if(getIntent().getStringExtra("BarcodeData") != null) {
            text.setText(getIntent().getStringExtra("BarcodeData"));
        }
    }

}
