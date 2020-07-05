package com.example.filemanagement.ui.batchProcessing;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.filemanagement.R;
import com.example.filemanagement.activity_plan_to_send_barcode_bp;

public class BatchProcessingFragment extends Fragment {

    private BatchProcessingViewModel batchProcessingViewModel;
    private Button plant_to_send_btn = null;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        batchProcessingViewModel = ViewModelProviders.of(this).get(BatchProcessingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_batch_processing, container, false);
//        final TextView textView = root.findViewById(R.id.textBPO);
//        batchProcessingViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        //-------------------------------------jai aahh part dekhli---------------------------------------------------
        plant_to_send_btn = root.findViewById(R.id.plan_to_send_bp);
        plant_to_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), activity_plan_to_send_barcode_bp.class);
                startActivity(intent);
            }
        });

        //-----------------------------------------------------------------------------------------------------------------
        return root;
    }
}