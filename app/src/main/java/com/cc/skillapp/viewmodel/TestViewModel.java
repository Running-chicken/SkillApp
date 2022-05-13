package com.cc.skillapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;

import com.cc.skillapp.BR;
import com.cc.skillapp.R;
import com.cc.skillapp.entity.TestLisTEntity;
import com.cc.skillapp.entity.TestLisTEntity.*;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class TestViewModel extends AndroidViewModel {

    public ObservableArrayList<TestLisTEntity.MenuIcon> items;
    public ItemBinding<MenuIcon> itemBinding;


    public TestViewModel(@NonNull Application application) {
        super(application);
        items = new ObservableArrayList<>();
        itemBinding = ItemBinding.<MenuIcon>of(BR.data,R.layout.data_binding_menu_item).bindExtra(BR.model,this);
    }



}
