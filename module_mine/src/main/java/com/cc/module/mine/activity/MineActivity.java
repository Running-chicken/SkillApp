package com.cc.module.mine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContentValues;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cc.library.base.interfaces.ResponseListener;
import com.cc.library.base.util.RouterPath;
import com.cc.library.base.util.Utils;
import com.cc.module.mine.entity.MenuIconBean;
import com.cc.module.mine.entity.MineDB;
import com.cc.module.mine.viewmodel.MineViewModel;
import com.example.module_mine.R;
import com.example.module_mine.databinding.MineActivityHostBinding;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.List;

import io.reactivex.disposables.Disposable;

@Route(path = RouterPath.Mine.MINE_HOUSE)
public class MineActivity extends AppCompatActivity {

    MineActivityHostBinding mBinding;
    MineViewModel mineViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.mine_activity_host);
        mineViewModel = new ViewModelProvider(this).get(MineViewModel.class);
        mBinding.setViewModel(mineViewModel);

        mineViewModel.getMenuIcon(new ResponseListener<List<MenuIconBean>>() {
            @Override
            public void loadSuccess(List<MenuIconBean> data) {
                Utils.log(getClass(),"finally size:"+data.size());
            }

            @Override
            public void loadFailed(String errorCode, String errorMsg) {

            }

            @Override
            public void addDisposable(Disposable disposable) {

            }
        });

        mBinding.tvAdd.setOnClickListener(view -> {
            MineDB mineDB = new MineDB();
            mineDB.setName(mBinding.etInputName.getText().toString());
            mineDB.setAge(Integer.parseInt(mBinding.etInputAge.getText().toString()));
            mineDB.save();
        });

        mBinding.tvQuery.setOnClickListener(view -> {
            MineDB mineDB = LitePal.findFirst(MineDB.class);
            Utils.log("输出mine:"+mineDB.getName());
        });

        mBinding.tvUpdate.setOnClickListener(view -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name",mBinding.etInputName.getText().toString());
            LitePal.updateAll(MineDB.class,contentValues,"age=?","29");
        });

        mBinding.tvDelete.setOnClickListener(view -> {
            LitePal.deleteAll(MineDB.class,"name=?",mBinding.etInputName.getText().toString());
        });
    }


}