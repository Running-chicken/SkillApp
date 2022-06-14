package com.cc.module.test;

import android.app.Application;

import com.cc.library.base.interfaces.ModuleInitImpl;
import com.cc.library.base.util.Utils;

public class TestModuleInit implements ModuleInitImpl {
    @Override
    public boolean onInitAhead(Application application) {
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        Utils.log("this is test module low");
        return false;
    }
}
