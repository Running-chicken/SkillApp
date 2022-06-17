package com.cc.module.test.entity.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashSet;

public class ReferenceDemo {

    public static ReferenceQueue<TestReference> queue = new ReferenceQueue<>();

    public static void main(String[] args) {
//        testSoftRef();
//        testPhantom();
        testWeak();

    }



    //软引用不会被加入队列
    public static void testSoftRef(){
        HashSet<SoftReference<TestReference>> softReferences =new HashSet<>();
        for (int i=0;i<10;i++){
            SoftReference<TestReference> soft = new SoftReference<TestReference>(new TestReference("soft"+i),queue);
            System.out.println("newSoft "+soft.get().toString());
            softReferences.add(soft);
        }
        System.gc();
        checkQueue();
    }

    public static void testPhantom(){
        HashSet<PhantomReference<TestReference>> phantomReferences =new HashSet<>();

        for (int i=0;i<10;i++){
            PhantomReference<TestReference> phantomReference = new PhantomReference<TestReference>(new TestReference("phantom"+i),queue);
            System.out.println("phantom..."+phantomReference.get());
            phantomReferences.add(phantomReference);

        }
        System.gc();
        checkQueue();
    }

    public static void testWeak(){
        HashSet<WeakReference<TestReference>> weakReferences =new HashSet<>();
        for (int i=0;i<10;i++){
            WeakReference<TestReference> weakReference = new WeakReference<TestReference>(new TestReference("weak"+i),queue);
            System.out.println("newWeak   "+weakReference.get().toString());
            weakReferences.add(weakReference);

        }
        System.gc();
        checkQueue();
    }


    public static void checkQueue(){
        if(queue!=null){
            Reference<TestReference> ref = (Reference<TestReference>) queue.poll();
            if(ref!=null){
                System.out.println("queue "+ref.get());
            }
        }
    }
}
