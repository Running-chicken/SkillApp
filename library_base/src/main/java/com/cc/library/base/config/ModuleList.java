package com.cc.library.base.config;

public class ModuleList {


    private static final String MineModuleInit = "com.cc.module.mine.MineModuleInit";

    private static final String TestModuleInit = "com.cc.module.test.TestModuleInit";


    public static String[] initModuleNames = new String[]{
            MineModuleInit,
            TestModuleInit
    };

}
