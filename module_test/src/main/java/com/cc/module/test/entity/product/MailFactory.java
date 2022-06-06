package com.cc.module.test.entity.product;

public class MailFactory implements Produce{
    @Override
    public Sender produce() {
        return new MailImpl();
    }
}
