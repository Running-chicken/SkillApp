package com.cc.skillapp.test.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cc.skillapp.R;
import com.cc.skillapp.utils.Utils;

public class TestFragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Utils.log(getClass(),"onCreateView");
        return inflater.inflate(R.layout.fragment_test1,container,false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.log(getClass(),"onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        Utils.log(getClass(),"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Utils.log(getClass(),"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Utils.log(getClass(),"onStop");
    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.log(getClass(),"onStart");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Utils.log(getClass(),"onAttach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.log(getClass(),"onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Utils.log(getClass(),"onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Utils.log(getClass(),"onDetach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Utils.log(getClass(),"onActivityCreated");
    }
}
