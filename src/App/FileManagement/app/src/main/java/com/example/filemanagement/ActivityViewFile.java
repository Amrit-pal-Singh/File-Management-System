package com.example.filemanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityViewFile extends AppCompatActivity {
    private PlaceHolderRestApi placeHolderRestApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_file);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://63daa718bc0c.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        placeHolderRestApi = retrofit.create(PlaceHolderRestApi.class);

        getPosts();

    }


    private void getPosts(){
        Call<List<Post>> call = placeHolderRestApi.getPosts("Token 26eb401a1957223c085e8f62062332e2e35521cd");

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                final ListView list = findViewById(R.id.myFilesList);
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add("File 1");
                arrayList.add("File 2");
                arrayList.add("File 3");
                arrayList.add("File 4");

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ActivityViewFile.this,
                        android.R.layout.simple_list_item_1, arrayList);

                list.setAdapter(arrayAdapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String clickedItem=(String) list.getItemAtPosition(position);
                        Toast.makeText(ActivityViewFile.this,clickedItem,Toast.LENGTH_LONG).show();
                    }
                });

                //List<Post> posts = response.body();
                for (int i=0; i < 2; i++){
                    //for (Post post : posts){
                    String content = "Success: ";
//                    content += "ID: " + post.getId() + "\n";
//                    content += "User ID: " + post.getUserId() + "\n";
//                    content += "Title: " + post.getTitle() + "\n";
//                    content += "Text: " + post.getText() + "\n\n";

                    Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}