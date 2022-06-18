package com.cc.module.test.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyVp2FragmentAdapter extends FragmentStateAdapter {

    List<Fragment> fragments;

    public MyVp2FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments = new ArrayList<>();
    }

    public void addFragment(Fragment fragment){
        this.fragments.add(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
