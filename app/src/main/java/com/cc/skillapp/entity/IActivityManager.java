package com.cc.skillapp.entity;

import android.content.Intent;
import android.os.IInterface;

public interface IActivityManager extends IInterface {

    String DESCRIPTOR = "cc.skill.IActivityManager";


    int startActivity(Intent intent);

}
