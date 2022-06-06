package com.cc.module.test.entity.fanxing;

import retrofit2.http.PUT;

public class GenericTest {


    public static void main(String[] args) {
        Box<String> box = new Box<String>("cc");
        Box<Integer> boxInt = new Box<Integer>(29);
        Box<Number> boxNum = new Box<Number>(30);
//        System.out.println(box.getData());
//        System.out.println(boxInt.getData());
//        System.out.println(boxNum.getData());

//        getData(box);
        getData(boxInt);
        getData(boxNum);
    }

    static public void getData(Box<? extends Number> box){
        System.out.println(box.data);
    }

    static class Box<T>{
        private T data;

        public Box(){}
        public Box(T data){
            this.data = data;
        }

        public T getData(){
            return data;
        }
    }

}
