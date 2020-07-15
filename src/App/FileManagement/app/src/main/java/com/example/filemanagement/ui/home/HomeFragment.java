package com.example.filemanagement.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.filemanagement.ActivityViewFile;
import com.example.filemanagement.LoginActivity;
import com.example.filemanagement.PlaceHolderRestApi;
import com.example.filemanagement.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private List<String> categories = new ArrayList<String>();
    private Spinner roleSelectorSpinner;
    private Button viewFiles;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        //
        try {
            roleSelectorSpinner = root.findViewById(R.id.role_selector);
        } catch (Exception e) {
            Log.e(TAG, "Spinner Role Selector Error!!");
        }

        if(categories.isEmpty()) {
            getRoles();
            if(categories.isEmpty())
                categories.add("No Roles");
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        roleSelectorSpinner.setAdapter(dataAdapter);

        roleSelectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedString = roleSelectorSpinner.getItemAtPosition(position).toString();
                if(selectedString != "Select Role"){
//                    Toast.makeText(getContext(), "You Selected "+selectedString, Toast.LENGTH_SHORT).show();
                    Snackbar.make(root, "You Selected "+selectedString, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewFiles = root.findViewById(R.id.viewFiles_button);
        viewFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActivityViewFile.class));
            }
        });

        return root;
    }

    private void getRoles(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PlaceHolderRestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceHolderRestApi placeHolderRestApi = retrofit.create(PlaceHolderRestApi.class);

        //Token for Jai: "Token a177092974d276852aa8c638cf6823e0f1a89972"
        if(LoginActivity.TOKEN == null){
            categories.add("NULL TOKEN");
        }

        Call<JsonObject> call = placeHolderRestApi.getRoles(LoginActivity.TOKEN);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(!response.isSuccessful()){
                    categories.add("Unsuccessful: " + response.code());
                    return;
                }

                JsonObject jsonObject = response.body();

                for(Map.Entry<String, JsonElement> entry: jsonObject.entrySet()){
                    JsonObject jsonRoles = entry.getValue().getAsJsonObject();
                    StringBuilder role = new StringBuilder();
                    try {
                        role.append(jsonRoles.get("name").toString()).append(" | ");
                        role.append(jsonRoles.get("department").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    categories.add(role.toString().replaceAll("\"", ""));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                categories.add("Failure: " + t.getMessage());
            }
        });
    }
}