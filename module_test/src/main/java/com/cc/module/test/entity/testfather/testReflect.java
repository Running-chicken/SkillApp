package com.cc.module.test.entity.testfather;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class testReflect {



    public static void main(String[] args) {
//        testExtends();
//        getClassFromName();
//        getClassField();
//        getMethods();
//        getConsstructor();
        getSuperClass();
    }

    public static void getClassFromName(){
        try {
            //forName方法会导致类加载 执行静态代码块
            Class classSon = Class.forName("com.cc.module.test.entity.testfather.Son");

            //无参构造方法
            Object son = classSon.newInstance();

            System.out.println(son.getClass().getSimpleName());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    public static void getClassField(){


        try {
            Class sonClass  = Class.forName("com.cc.module.test.entity.testfather.Son");
            Object object = sonClass.newInstance(); //调用无参构造方法，初始化
            Field[] fields = sonClass.getDeclaredFields();
            StringBuilder stringBuilder = new StringBuilder();
            //重要方法 Modifier.toString(class.getModifiers) 获取修饰符
            System.out.println(Modifier.toString(sonClass.getModifiers())
            +" class "+ sonClass.getSimpleName()+"\n");
            for(int i=0;i<fields.length;i++){
                stringBuilder.append(Modifier.toString(fields[i].getModifiers()));
                stringBuilder.append(" ");
                stringBuilder.append(fields[i].getName());
                stringBuilder.append("\n");
            }
            System.out.println(stringBuilder.toString());



            Field fieldName = sonClass.getDeclaredField("name");
            fieldName.set(object,"zhenzhen");
            System.out.println("输出属性："+fieldName.get(object));

            Field fieldAddress = sonClass.getDeclaredField("address");
            //重要方法 私有变量打破封装
            fieldAddress.setAccessible(true);
            fieldAddress.set(object,"平谷区");
            System.out.println("输出address:"+fieldAddress.get(object));


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }

    public static void getMethods(){
        try {
            //forName方法会导致类加载 执行静态代码块
            Class classSon = Class.forName("com.cc.module.test.entity.testfather.Son");

            //无参构造方法
            Object son = classSon.newInstance();

            StringBuilder stringBuilder = new StringBuilder();
            Method[] methods = classSon.getDeclaredMethods();
            for(int i=0;i<methods.length;i++){
                stringBuilder.append(Modifier.toString(methods[i].getModifiers()));
                stringBuilder.append(" ");
                stringBuilder.append(methods[i].getName());
                stringBuilder.append(" params:");
                Class[] params = methods[i].getParameterTypes();
                for(int j=0;j<params.length;j++){
                    stringBuilder.append(params[j].getSimpleName());
                    stringBuilder.append(" ");
                }
                stringBuilder.append(" returnType:");
                stringBuilder.append(methods[i].getReturnType().getSimpleName());
                stringBuilder.append("\n");
            }

            System.out.println(stringBuilder.toString());

            Method method = classSon.getDeclaredMethod("getName",boolean.class);
            Object result = method.invoke(son,true);
            System.out.println("结果是："+result);



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public static void getConstructor(){
        try {
            //forName方法会导致类加载 执行静态代码块
            Class classSon = Class.forName("com.cc.module.test.entity.testfather.Son");



            Constructor[] constructors = classSon.getDeclaredConstructors();
            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0;i<constructors.length;i++){
                stringBuilder.append(Modifier.toString(constructors[i].getModifiers()));
                stringBuilder.append(" ");
               stringBuilder.append(classSon.getSimpleName());
               stringBuilder.append(" ");
               Class[] params = constructors[i].getParameterTypes();
               for(int j=0;j<params.length;j++){
                   stringBuilder.append(params[j].getSimpleName());
                   stringBuilder.append(" ");
               }
               stringBuilder.append("\n");
            }

            System.out.println(stringBuilder.toString());


            Constructor constructor = classSon.getConstructor(String.class);

            Object object = constructor.newInstance("cuican");
            System.out.println(object);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    public static void getSuperClass(){
        try {
            //forName方法会导致类加载 执行静态代码块
            Class classSon = Class.forName("com.cc.module.test.entity.testfather.Son");
            Class superClass = classSon.getSuperclass();
            System.out.println(superClass.getSimpleName());

            Class[] interfaces = classSon.getInterfaces();
            for(int i=0;i<interfaces.length;i++){
                System.out.println(interfaces[i].getSimpleName());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void testExtends(){
        //子类实例化父类
        Father father = new Son();
        //父类只能调用自己的方法，如果被子类重写，会走子类逻辑
        father.sendFather();
    }

}
