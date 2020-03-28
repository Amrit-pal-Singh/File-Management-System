package com.cdac.qrcodescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    Intent intent_from_editProfile = null;
    TextView name = null;
    TextView phone = null;
    TextView email = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.tvNumber5);
        phone = findViewById(R.id.tvNumber1);
        email = findViewById(R.id.tvNumber3);

        Button edit_profile = (Button) findViewById(R.id.buttonEditProfile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
                finish();
            }
        });

        try {
            intent_from_editProfile = getIntent();
        }catch(Exception e) {
            Log.e(TAG, "Error!!!!", e);
        }
        if(intent_from_editProfile != null) {
            ArrayList<String> profile_values = intent_from_editProfile.getStringArrayListExtra("profile_values");
            if(profile_values != null && !profile_values.isEmpty()) {
                updateProfile(profile_values);
            }
        }

    }

    public void updateProfile(ArrayList<String> pv) {

        name.setText(pv.get(0));
        phone.setText(pv.get(1));
        email.setText(pv.get(2));

    }
}
