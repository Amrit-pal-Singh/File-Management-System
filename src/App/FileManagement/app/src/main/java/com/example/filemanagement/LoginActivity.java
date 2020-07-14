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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static String LOG_TAG = "Login Activity";

    private PlaceHolderRestApi placeHolderRestApi;
    public static String TOKEN = null;

    private int remove_it_later = 0;

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
        Toast.makeText(getApplicationContext(), "Trying to Login", Toast.LENGTH_SHORT).show();

        EditText mEmail = findViewById(R.id.editTextEmail);
        EditText mPassword = findViewById(R.id.editTextPassword);
        //textViewResult = findViewById((R.id.text_view_result));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PlaceHolderRestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        placeHolderRestApi = retrofit.create(PlaceHolderRestApi.class);

        apiLogin(mEmail.getText().toString(), mPassword.getText().toString());


        if(TOKEN == null || TOKEN.equals("Login_Unsuccessful") || TOKEN.equals("Login_Failed")){
            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
            remove_it_later++;
        }
        else {
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, FrontPageActivity.class));
        }
        if(remove_it_later > 2){
            startActivity(new Intent(this, FrontPageActivity.class));
        }
    }

    private void apiLogin(String email, String password){

        //Default login credentials for testing
        if(email.equals("")){
            email = "jai69@gmail.com";
            password = "new_pass_123";
        }

        LoginCredentials loginCredentials = new LoginCredentials(email, password);

        Call<LoginCredentials> call = placeHolderRestApi.apiLogin(loginCredentials);

        call.enqueue(new Callback<LoginCredentials>() {
            @Override
            public void onResponse(Call<LoginCredentials> call, Response<LoginCredentials> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Unsuccessful: " + response.code(), Toast.LENGTH_LONG).show();
                    TOKEN = "Login_Unsuccessful";
                    return;
                }

                LoginCredentials loginCredentials = response.body();

                TOKEN = "Token " + loginCredentials.getToken();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "Token: " + loginCredentials.getToken() + "\n";
                content += "Email: " + loginCredentials.getEmail() + "\n";
                content += "Full Name: " + loginCredentials.getFirst_name() + " "
                        + loginCredentials.getLast_name() + "\n";

                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<LoginCredentials> call, Throwable t) {
                TOKEN = "Login_Failed";
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
