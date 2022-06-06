package com.cc.module.test.entity.product;

public class MailImpl implements Sender{
    @Override
    public void sender() {
        System.out.println("this is mail");
    }
}
