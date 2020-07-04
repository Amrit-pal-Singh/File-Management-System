package com.example.filemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static String LOG_TAG = "Main Activity";

    private TextView textViewResult;
    private PlaceHolderRestApi placeHolderRestApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(LOG_TAG, "Entered in OnCreate Method");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById((R.id.text_view_result));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://612f39a4af3b.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        placeHolderRestApi = retrofit.create(PlaceHolderRestApi.class);

        //getPosts();
        createPost();
    }

//    private void getPosts(){
//        Call<List<Post>> call = placeHolderRestApi.getPosts();
//
//        call.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                if(!response.isSuccessful()){
//                    textViewResult.setText("Code: " + response.code());
//                    return;
//                }
//
//                List<Post> posts = response.body();
//                for (Post post : posts){
//                    String content = "";
//                    content += "ID: " + post.getId() + "\n";
//                    content += "User ID: " + post.getUserId() + "\n";
//                    content += "Title: " + post.getTitle() + "\n";
//                    content += "Text: " + post.getText() + "\n\n";
//
//                    textViewResult.append((content));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                textViewResult.setText(t.getMessage());
//            }
//        });
//    }

    private void createPost(){
        Post post = new Post("jai69@gmail.com", "new_pass_123");

        Call<Post> call = placeHolderRestApi.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getUsername() + "\n";
                content += "User ID: " + postResponse.getPassword() + "\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }
}
