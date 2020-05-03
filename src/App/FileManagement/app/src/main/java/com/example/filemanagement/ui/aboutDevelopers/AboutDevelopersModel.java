package com.example.filemanagement.ui.aboutDevelopers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutDevelopersModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AboutDevelopersModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is About Developers fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}