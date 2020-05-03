package com.example.filemanagement.ui.recieve;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecieveViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RecieveViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Recieve fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}