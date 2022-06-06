package com.cc.module.test.entity.product;

public class SmsFactory implements Produce{
    @Override
    public Sender produce() {
        return new SmsImpl();
    }
}
