package com.cc.module.test.entity.builder;

import com.cc.module.test.entity.product.MailImpl;
import com.cc.module.test.entity.product.Sender;
import com.cc.module.test.entity.product.SmsImpl;

import java.util.ArrayList;
import java.util.List;

public class BuilderDemo {

    List<Sender> mList = new ArrayList<>();

    public void produceMail(){
        mList.add(new MailImpl());
    }

    public void produceSms(){
        mList.add(new SmsImpl());
    }
}
