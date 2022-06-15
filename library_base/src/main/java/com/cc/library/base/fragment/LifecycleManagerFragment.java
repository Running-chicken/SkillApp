package com.cc.library.base.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;


import com.cc.library.base.entity.ActivityFragmentLifecycle;

public class LifecycleManagerFragment extends Fragment {


    private final ActivityFragmentLifecycle lifecycle;


    public LifecycleManagerFragment() {
        this(new ActivityFragmentLifecycle());
    }

    // For testing only.
    @SuppressLint("ValidFragment")
    LifecycleManagerFragment(ActivityFragmentLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    public ActivityFragmentLifecycle getLifecycle() {
        return lifecycle;
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycle.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycle.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycle.onDestroy();
    }

}
