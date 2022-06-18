package com.cc.module.test.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.cc.module.test.BR;

public class TestUser extends BaseObservable {

    private String name;

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }


    @Bindable
    public String getName() {
        return name;
    }
}
