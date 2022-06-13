package com.cc.skillapp.test.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cc.library.base.util.StringUtils;
import com.cc.skillapp.R;
import com.cc.library.base.util.Utils;
import com.cc.skillapp.databinding.FragmentTest1Binding;

public class TestFragment1 extends Fragment {

    FragmentTest1Binding mBinding;
    FragmentViewModel fragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Utils.log(getClass(),"onCreateView");

        mBinding = FragmentTest1Binding.inflate(inflater,container,false);
        fragmentViewModel = new ViewModelProvider(getActivity()).get(FragmentViewModel.class);
        mBinding.setViewModel(fragmentViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.tvChange.setOnClickListener(view1 -> {
            fragmentViewModel.params1.set("fg2:我是你大爷");
        });

        fragmentViewModel.liveData1.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(!StringUtils.isNullOrEmpty(s)){
                    mBinding.tvLivedata.setText(s);
                }
            }
        });

        Bundle bundle = getArguments();
        if(bundle!=null){
            String params = bundle.getString("params");
            if(!StringUtils.isNullOrEmpty(params)){
                mBinding.tvArg.setText(params);
            }
        }

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
