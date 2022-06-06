package com.cc.module.test.entity.product;

import com.cc.module.test.entity.builder.BuilderDemo;
import com.cc.module.test.entity.product.MailFactory;
import com.cc.module.test.entity.product.Produce;
import com.cc.module.test.entity.product.Sender;
import com.cc.module.test.entity.product.SmsFactory;
import com.cc.module.test.entity.reference.TestReference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashSet;

public class TestModel {

    public static void main(String[] args) {

//        testStrong();
        testVoid();


    }



    private static void testVoid(){
        String str = "hello";
        ReferenceQueue<? super String> queue = new ReferenceQueue<>();
        PhantomReference<String> phantomReference = new PhantomReference<>(str,queue);
        System.out.println("虚引用："+ phantomReference.get());
        System.out.println("虚引用："+ queue.poll());
    }

    private static void testStrong(){
        String str = "hello";
        SoftReference<String> softReference = new SoftReference<>(str);
        System.out.println(softReference.get());
    }

    private void testProduct(){
        Produce produce = new MailFactory();
//        Produce produce = new SmsFactory();
        Sender sender = produce.produce();
        sender.sender();
    }

    private void testBuild(){
        BuilderDemo builderDemo = new BuilderDemo();
        builderDemo.produceMail();
    }

}
