package com.cc.module.mine.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cc.library.base.interfaces.ResponseListener;
import com.cc.library.base.netconfig.RetrofitManager;
import com.cc.library.base.observer.ResponseObserver;
import com.cc.library.base.util.RxUtils;
import com.cc.module.mine.api.MineApi;
import com.cc.module.mine.entity.MenuIconBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineViewModel extends AndroidViewModel {
    public MineViewModel(@NonNull Application application) {
        super(application);
    }


    public void getMenuIcon(ResponseListener<List<MenuIconBean>> listener){

        Map<String,Object> params = new HashMap<>();
        params.put("platform",1);
        params.put("parentId",0);

        RetrofitManager.create(MineApi.class)
                .getMenuIcon(params)
                .compose(RxUtils.responseTransformer())
                .compose(RxUtils.schedulersTransformer())
                .subscribe(new ResponseObserver<List<MenuIconBean>>() {
                    @Override
                    public void onSuccess(List<MenuIconBean> data) {
                        listener.loadSuccess(data);
                    }
                });

    }

}
