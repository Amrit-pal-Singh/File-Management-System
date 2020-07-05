package com.example.filemanagement.ui.batchProcessing;

import android.content.Intent;
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
import com.example.filemanagement.activity_plan_to_send_barcode_bp;

public class BatchProcessingFragment extends Fragment {

    private BatchProcessingViewModel batchProcessingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        batchProcessingViewModel =
                ViewModelProviders.of(this).get(BatchProcessingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_batch_processing, container, false);
        final TextView textView = root.findViewById(R.id.textBPO);
        batchProcessingViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }
}