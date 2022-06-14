package com.cc.module.mine;

import android.app.Application;

import com.cc.library.base.interfaces.ModuleInitImpl;
import com.cc.library.base.util.Utils;

public class MineModuleInit implements ModuleInitImpl {
    @Override
    public boolean onInitAhead(Application application) {
        Utils.log(getClass(),"this is mine module init");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        return false;
    }
}
