package com.example.filemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class activity_view_details_of_a_file extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details_of_a_file);

        TextView tvQR = (TextView) findViewById(R.id.qr);
        tvQR.setText("1234");

        TextView tvName = (TextView) findViewById(R.id.name);
        tvName.setText("Grade Submission File");
    }
}