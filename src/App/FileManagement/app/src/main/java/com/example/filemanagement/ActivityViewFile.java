package com.example.filemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityViewFile extends AppCompatActivity {
    Button b1, b2, b3, b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_file);
        b1 = findViewById(R.id.generated_files_by_me);
        b1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), activity_view_generated_files_by_me.class);
            startActivity(intent);
        });

        b2 = findViewById(R.id.app_disapp_files_by_me);
        b2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), activity_view_app_disapp_files_by_me.class);
            startActivity(intent);
        });

        b3 = findViewById(R.id.my_plan_to_send_files);
        b3.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), activity_view_my_files_plan_to_send.class);
            startActivity(intent);
        });

        b4 = findViewById(R.id.app_disapp_files_by_others);
        b4.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), activity_view_files_app_disapp_by_others.class);
            startActivity(intent);
        });

    }

}