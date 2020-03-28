package com.cdac.qrcodescanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TrackfileActivity extends AppCompatActivity {

    String[] array = {"File 1","File 2","File 3","File 4", "File 5","File 6","File 7","File 8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackfile);

        ListView list_view = findViewById(R.id.list_trackfile);
        ArrayAdapter<String> array_adapter = new ArrayAdapter<String>(this, R.layout.trackfile_list_row, array);
        list_view.setAdapter(array_adapter);

    }
}
