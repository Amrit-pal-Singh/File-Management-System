package com.example.filemanagement.ui.batchProcessing;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BatchProcessingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BatchProcessingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Batch Processing fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}