package com.example.filemanagement.ui.planToSend;

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

public class PlanToSendFragment extends Fragment {

    private PlanToSendViewModel planToSendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        planToSendViewModel =
                ViewModelProviders.of(this).get(PlanToSendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_plan_to_send, container, false);
        final TextView textView = root.findViewById(R.id.text_plan_to_send);
        planToSendViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}