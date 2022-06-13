package com.cc.skillapp.test.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class FragmentViewModel extends AndroidViewModel {

    public ObservableField<String> params = new ObservableField<>();
    public ObservableField<String> params1 = new ObservableField<>();

    public MutableLiveData<String> liveData1 = new MutableLiveData<>();


    public FragmentViewModel(@NonNull Application application) {
        super(application);
        params.set("这是参数1");
        params1.set("这是参数2");
    }

}
