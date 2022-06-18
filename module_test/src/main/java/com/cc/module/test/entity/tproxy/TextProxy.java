package com.cc.module.test.entity.tproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TextProxy {

    public static void main(String[] args) {



        MyTestApi proxy = (MyTestApi) Proxy.newProxyInstance(
                MyTestApi.class.getClassLoader(),
                new Class[]{MyTestApi.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        return method.invoke(new MyTestApiImpl(),objects);
                    }
                }

        );
        proxy.add(1,2);


//        MyTestApi proxy1 = new MyTestApiImpl();
//        proxy1.showName("cuican");

    }

}
