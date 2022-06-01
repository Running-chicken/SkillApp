package com.cc.module.test.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;

import com.cc.library.base.entity.Person;
import com.cc.module.test.R;

import me.tatarka.bindingcollectionadapter2.BR;
import me.tatarka.bindingcollectionadapter2.ItemBinding;


public class RxJavaViewModel extends AndroidViewModel {

    public ObservableArrayList<Person> items;
    public ItemBinding<Person> itemBinding;


    public RxJavaViewModel(@NonNull Application application) {
        super(application);

        items = new ObservableArrayList<Person>();
        itemBinding = ItemBinding.of(BR.data, R.layout.test_person_list_item);

    }
}
