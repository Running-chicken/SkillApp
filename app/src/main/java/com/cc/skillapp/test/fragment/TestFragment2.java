package com.cc.skillapp.test.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cc.library.base.entity.BaseEventBus;
import com.cc.library.base.util.Utils;
import com.cc.skillapp.R;
import com.cc.skillapp.databinding.FragmentTest1Binding;
import com.cc.skillapp.databinding.FragmentTest2Binding;
import com.cc.skillapp.fragment.FragmentTest1;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class TestFragment2 extends Fragment {

    FragmentTest2Binding mBinding;
    FragmentViewModel fragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Utils.log(getClass(),"onCreateView");

        mBinding = FragmentTest2Binding.inflate(inflater,container,false);
        fragmentViewModel = new ViewModelProvider(getActivity()).get(FragmentViewModel.class);
        mBinding.setViewModel(fragmentViewModel);
        return mBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.tvChange.setOnClickListener(view1 -> {
            fragmentViewModel.params.set("fg1:我是你弟弟");
        });

        mBinding.tvLivedata.setOnClickListener(view1 -> {
            fragmentViewModel.liveData1.setValue("修改你的liveData");
        });

        mBinding.tvEventBus.setOnClickListener(view1 ->{
            EventBus.getDefault().post(new BaseEventBus("act","我来自fg2"));
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.log(getClass(),"onCreate");

        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void testHaHa(BaseEventBus baseEventBus){
        if("frag".equals(baseEventBus.getType())){
            Utils.log(getClass(),baseEventBus.getMsg());
        }
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
        EventBus.getDefault().unregister(this);

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
