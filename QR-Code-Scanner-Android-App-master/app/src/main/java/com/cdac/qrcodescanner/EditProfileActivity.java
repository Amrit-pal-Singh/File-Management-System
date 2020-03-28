package com.cdac.qrcodescanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.internal.ListenerClass;

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = "EditProfileActivity";

    EditText input_name = null;
    EditText input_phone = null;
    EditText input_email = null;
    Button btn_save_profile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        input_name = findViewById(R.id.input_name);
        input_phone = findViewById(R.id.input_phone);
        input_email = findViewById(R.id.input_email);
        btn_save_profile = findViewById(R.id.btn_save_profile);

        btn_save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfile();
            }
        });
    }

    public void saveProfile() {
        Log.d(TAG, "saveProfile");

        if(!validate()) {
            EditProfileFailed();
        }else {
            EditProfileSuccess();
        }
    }

    public void EditProfileSuccess() {
        final ProgressDialog progressDialog = new ProgressDialog(EditProfileActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Changing Profile...");
        progressDialog.show();
        // TODO: send this info to server and change profile page.

        ArrayList<String> profile_values = new ArrayList<>();
        profile_values.add(input_name.getText().toString());
        profile_values.add(input_phone.getText().toString());
        profile_values.add(input_email.getText().toString());

        startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class).putStringArrayListExtra("profile_values", profile_values));

        finish();
    }

    public void EditProfileFailed() {
        Toast.makeText(getBaseContext(), "Profile Info Not complete/Invalid", Toast.LENGTH_SHORT).show();
    }

    public boolean validate(){
        boolean valid = true;

        String name_edit_text = input_name.getText().toString();
        String phone_edit_text = input_phone.getText().toString();
        String email_edit_text = input_email.getText().toString();

        if(name_edit_text.isEmpty()) {
            input_name.setError("Cannot be empty");
            valid = false;
        }else {
            input_name.setError(null);
        }
        if(phone_edit_text.isEmpty() || phone_edit_text.length() > 10) {
            if(phone_edit_text.isEmpty()) {
                input_phone.setError("Phone Number must be of length greater than 0");
                valid=false;
            }
            else if(phone_edit_text.length() > 10) {
                input_phone.setError("Phone Number length is more than 10");
                valid=false;
            }
            else {
                input_phone.setError(null);
            }
        }
        if(email_edit_text.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email_edit_text).matches()) {
            input_email.setError("Invalid Email");
            valid=false;
        }else{
            input_email.setError(null);
        }

        return valid;
    }
}
