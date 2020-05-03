package com.example.filemanagement.ui.planToSend;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlanToSendViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PlanToSendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Plan To send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}