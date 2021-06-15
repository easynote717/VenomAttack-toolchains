package com.s3lab.easynote.ui.myself;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyselfViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyselfViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

