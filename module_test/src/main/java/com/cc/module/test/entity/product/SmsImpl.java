package com.cc.module.test.entity.product;

public class SmsImpl implements Sender{
    @Override
    public void sender() {
        System.out.println("this is sms");
    }
}
