package com.example.doorlockproject.ui.period;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PeriodViewModel extends ViewModel {
    Button unlock;
    private MutableLiveData<String> mText;

    public PeriodViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }
        public LiveData<String> getText() {
        return mText;
    }
}