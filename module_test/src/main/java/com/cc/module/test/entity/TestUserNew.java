package com.cc.module.test.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.cc.module.test.BR;

public class TestUserNew {

    public ObservableField<String> name = new ObservableField<>();

    public MutableLiveData<String> alasName = new MutableLiveData<>();


}
