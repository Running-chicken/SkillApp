package com.cc.skillapp.test.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.cc.library.base.entity.BaseEventBus;
import com.cc.library.base.util.Utils;
import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityTestFragmentBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Random;

public class TestFragmentActivity extends AppCompatActivity {


    FragmentViewModel fragmentViewModel;
    String[] strings = new String[]{"aaa","bbb","ccc","ddd","eee","fff","111","222","333","555"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTestFragmentBinding mBinding = DataBindingUtil.setContentView(this,R.layout.activity_test_fragment);

        fragmentViewModel = new ViewModelProvider(this).get(FragmentViewModel.class);

        //传值方式 1.viewModel

        mBinding.tvChangeParams.setOnClickListener(view -> {
            fragmentViewModel.params.set("fragment1展示"+strings[new Random().nextInt(10)]);
            fragmentViewModel.params1.set("fragment2展示"+strings[new Random().nextInt(10)]);
        });

        Fragment testFragment1 = new TestFragment1();
        //传值方式2 setArguments
        Bundle bundle = new Bundle();
        bundle.putString("params","崔粲");
        testFragment1.setArguments(bundle);
        Fragment testFragment2 = new TestFragment2();


        mBinding.tvChangeFragment.setOnClickListener(view -> {

            getSupportFragmentManager().beginTransaction().hide(testFragment2).show(testFragment1).commit();
        });

        mBinding.tvChangeFragment1.setOnClickListener(view -> {
            if(!testFragment2.isAdded()){
                getSupportFragmentManager().beginTransaction().hide(testFragment1).add(R.id.fl_root,testFragment2).commit();
            }else{
                getSupportFragmentManager().beginTransaction().hide(testFragment1).show(testFragment2).commit();
            }
        });



        getSupportFragmentManager().beginTransaction().add(R.id.fl_root,testFragment1).commit();


        EventBus.getDefault().register(this);


        mBinding.tvActivityEventbus.setOnClickListener(view -> {
            EventBus.getDefault().post(new BaseEventBus("frag","我来自act"));

        });
    }

    @Subscribe
    public void eventBusAct(BaseEventBus baseEventBus){
        if(baseEventBus.getType().equals("act")){

            Utils.log(getClass(),baseEventBus.getMsg());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}