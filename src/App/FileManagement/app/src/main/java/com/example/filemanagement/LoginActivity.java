package com.example.filemanagement;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static String LOG_TAG = "Login Activity";

    public static String TOKEN = null;

    private int remove_this_var_later = 0;

    public static String username_fixed;
    public static String email_fixed;
    public static String department_fixed;
    public static String firstName_fixed;
    public static String lastName_fixed;
    public static String role_fixed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        changeStatusBarColor();
        Toast.makeText(getApplicationContext(), "Login Activity", Toast.LENGTH_SHORT).show();
    }

    // Functions
    private void init(String username, String email, String firstName, String lastName, String role) {
        username_fixed = username;
        email_fixed = email;
        firstName_fixed = firstName;
        lastName_fixed = lastName;
        role_fixed = role;
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.login_bk_color));
        }
    }

    public void onLoginClick(View View) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }

    public void Login(View View) {

        if (remove_this_var_later > 4){
            startActivity(new Intent(getApplicationContext(), FrontPageActivity.class));
        }

        remove_this_var_later++;

        EditText mEmail = findViewById(R.id.editTextEmail);
        EditText mPassword = findViewById(R.id.editTextPassword);

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        //Default login credentials for testing
        if(email.equals("")){
            email = "jai69@gmail.com";
            password = "new_pass_123";
        }

        Toast.makeText(getApplicationContext(), "Trying to Login", Toast.LENGTH_LONG).show();

        LoginCredentials loginCredentials = new LoginCredentials(email, password);

        Call<LoginCredentials> call = PlaceHolderRestApi.restApi.apiLogin(loginCredentials);

        call.enqueue(new Callback<LoginCredentials>() {
            @Override
            public void onResponse(Call<LoginCredentials> call, Response<LoginCredentials> response) {
                if(response.isSuccessful()) {


                    LoginCredentials loginCredentials = response.body();

                    assert loginCredentials != null;
                    TOKEN = "Token " + loginCredentials.getToken();

                    String content = "Welcome ";
//                    content += "Code: " + response.code() + "\n";
//                    content += "Email: " + loginCredentials.getEmail() + "\n";
                    content += loginCredentials.getFirst_name() + " "
                            + loginCredentials.getLast_name() + "\n";

                    init(loginCredentials.getUsername(),
                            loginCredentials.getEmail(),
                            loginCredentials.getFirst_name(),
                            loginCredentials.getLast_name(),
                            "No Role");

                    Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getApplicationContext(), FrontPageActivity.class));

                }
                else{
                    Toast.makeText(getApplicationContext(), "Unsuccessful: " + response.code(), Toast.LENGTH_LONG).show();
                    TOKEN = "Login_Unsuccessful";
                }
            }

            @Override
            public void onFailure(Call<LoginCredentials> call, Throwable t) {
                TOKEN = "Login_Failed";
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
