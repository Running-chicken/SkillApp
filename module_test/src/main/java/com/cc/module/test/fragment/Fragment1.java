package com.cc.module.test.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cc.library.base.util.Utils;
import com.cc.module.test.R;

public class Fragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.test_layout_fg1,container,false);

        Utils.log(getClass(),"onCreateView"+ name);
        return rootView;
    }

    private String name;
    public void setName(String name){
        this.name = name;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Utils.log(getClass(),"onDestroyView"+ name);
    }



}
