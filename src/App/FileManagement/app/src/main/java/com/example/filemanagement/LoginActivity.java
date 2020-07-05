package com.example.filemanagement;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static String LOG_TAG = "Login Activity";

    private TextView textViewResult;
    private PlaceHolderRestApi placeHolderRestApi;

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
                .baseUrl("http://63daa718bc0c.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        placeHolderRestApi = retrofit.create(PlaceHolderRestApi.class);

        createPost(mEmail.getText().toString(), mPassword.getText().toString());

        startActivity(new Intent(this, FrontPageActivity.class));
    }

    private void createPost(String email, String password){
        Post post = new Post(email, password);

        Call<Post> call = placeHolderRestApi.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful()){
                    //textViewResult.setText("Code: " + response.code());
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getUsername() + "\n";
                content += "User ID: " + postResponse.getPassword() + "\n";

                //textViewResult.setText(content);
                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
