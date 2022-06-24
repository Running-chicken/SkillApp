package com.cc.module.test.testnew;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class FanXing {

    public static void main(String[] args) {
        //泛型类测试
//        int b = new Test1<Integer>().setA(1);
//        System.out.println("输出："+b);

        //泛型接口测试
//        TestInterface<String> testInterface = new TestInterface<String>() {
//            @Override
//            public String show(String s) {
//
//                return s;
//            }
//        };
//        System.out.println(testInterface.show("cuican"));

        //泛型方法测试
//        System.out.println(testFun("cancan"));

        test2();

//        testSuper();
//        testExtend();
//        System.out.println(new Test1<String>().getClass().getName());
//        reflectTest1();

    }

    //final修饰必须在所有构造方法初始化
    public static class Z{
        final String a;
        Z(){
            this.a = "cuican";
        }
        Z(String a){
            this.a = a;
        }
    }



    //泛型通配符限定类别
    public static void test2(){
        List<?> list= new ArrayList<>();
        List<M> list1 = new ArrayList<>();
        list1.add(new M("cc"));
        list1.add(new N("nn"));
        list = list1;
        System.out.println(list.size());
    }
    //泛型通配符超类
    public static void testSuper(){
        List<? super M> list= new ArrayList<>();
        List<L> list1 = new ArrayList<>();
        list1.add(new L("cc"));
        list1.add(new M("dd"));
        list = list1;
        System.out.println(list.get(0));
    }
    //反省通配符子类
    public static void testExtend(){
        List<? extends M> list= new ArrayList<>();
        List<N> list1 = new ArrayList<>();
        list1.add(new N("cc"));
        list = list1;
        System.out.println(list.get(0));
    }

    public static class L{
        public L(String name){
            System.out.println(name);
        }
    }

    public static class M extends L{
        public M(String name){
            super(name);
        }
    };

    public static class N extends M{
        public N(String name) {
            super(name);
        }
    };


    //反射泛型
    public static void reflectTest1(){
        try {
            Class classTest = Class.forName("com.cc.module.test.testnew.FanXing$Test1");
            Object testIns = classTest.newInstance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Modifier.toString(classTest.getModifiers()));
            stringBuilder.append(classTest.getSimpleName());
            stringBuilder.append("{");
            stringBuilder.append("\n");
            Field[] fields = classTest.getDeclaredFields();
            for(int i=0;i<fields.length;i++){
                stringBuilder.append(Modifier.toString(fields[i].getModifiers()));
                stringBuilder.append(" ");
                stringBuilder.append(fields[i].getType().getSimpleName());
                stringBuilder.append(" ");
                stringBuilder.append(fields[i].getName());
                stringBuilder.append("\n");
            }
            stringBuilder.append("\n");
            stringBuilder.append("}");

            System.out.println(stringBuilder.toString());

        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }


    //泛型类
    public static final class Test1<T extends String>{
        private T t;


    }


    //泛型接口
    public interface TestInterface<T>{
        T show(T t);
    }


    //泛型方法
    private static  <T> T testFun(T t){
        return t;
    }


}
