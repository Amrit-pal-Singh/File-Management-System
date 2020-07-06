package com.example.filemanagement;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class activity_batch_processing_receive extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_processing_receive);

        TextView text = findViewById(R.id.lstScanR);
        if(getIntent().getStringExtra("BarcodeData") != null) {
            text.setText(getIntent().getStringExtra("BarcodeData"));
        }
    }
}
