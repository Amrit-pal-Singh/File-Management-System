package com.example.filemanagement.ui.generateFile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GenerateFileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GenerateFileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Generate File fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
