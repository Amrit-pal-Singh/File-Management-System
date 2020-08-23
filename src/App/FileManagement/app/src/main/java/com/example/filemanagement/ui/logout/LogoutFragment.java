package com.example.filemanagement.ui.logout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.filemanagement.FrontPageActivity;
import com.example.filemanagement.LoginActivity;
import com.example.filemanagement.R;

import java.util.Objects;

public class LogoutFragment extends Fragment {

    private LogoutViewModel logoutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        logoutViewModel =
                ViewModelProviders.of(this).get(LogoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_logout, container, false);

        destroyCredentialsData();

        startActivity(new Intent(this.getActivity(), LoginActivity.class));
        Objects.requireNonNull(this.getActivity()).finish();

//        final TextView textView = root.findViewById(R.id.text_logout);
//        logoutViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    public void destroyCredentialsData(){
        LoginActivity.TOKEN = null;

        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity())
                .getSharedPreferences(
                    LoginActivity.SHARED_PREFS,
                    Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(LoginActivity.MY_TOKEN, null);
        editor.putString(LoginActivity.USERNAME, null);
        editor.putString(LoginActivity.EMAIL, null);
        editor.putString(LoginActivity.FIRST_NAME, null);
        editor.putString(LoginActivity.LAST_NAME, null);

        editor.apply();
    }

}