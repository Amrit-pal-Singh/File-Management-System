package com.example.filemanagement.ui.aboutDevelopers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.filemanagement.R;

public class AboutDevelopersFragment extends Fragment {

    private AboutDevelopersModel aboutDevelopersModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutDevelopersModel =
                ViewModelProviders.of(this).get(AboutDevelopersModel.class);
        View root = inflater.inflate(R.layout.fragment_about_developers, container, false);
        final TextView textView = root.findViewById(R.id.text_about_developers);
        aboutDevelopersModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}