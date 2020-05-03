package com.example.filemanagement.ui.recieve;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.filemanagement.R;

public class RecieveFragment extends Fragment {

    private RecieveViewModel recieveViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recieveViewModel =
                ViewModelProviders.of(this).get(RecieveViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recieve, container, false);
        final TextView textView = root.findViewById(R.id.text_recieve);
        recieveViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}