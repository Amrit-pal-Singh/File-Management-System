package com.example.filemanagement.ui.approveDisapprove;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ApproveDisapproveViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ApproveDisapproveViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Approve/Disapprove fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}