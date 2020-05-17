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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.filemanagement.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private List<String> categories = new ArrayList<String>();
    private Spinner roleSelectorSpinner;
    private Button viewFiles;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        //Hard Coded Data

        categories.add("Select Role");
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        //
        try {
            roleSelectorSpinner = root.findViewById(R.id.role_selector);
        } catch (Exception e) {
            Log.e(TAG, "Spinner Role Selector Error!!");
        }
        if(categories.isEmpty()) {
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
                    Snackbar.make(view, "You Selected "+selectedString, Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
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
//              startActivity(new Intent(getContext(), Demo.class));
                Toast.makeText(getContext(), "Complete Intent", Toast.LENGTH_SHORT).show();

            }
        });

        return root;
    }
}