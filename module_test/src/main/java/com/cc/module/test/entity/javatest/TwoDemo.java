package com.cc.module.test.entity.javatest;

import java.math.BigInteger;

public class TwoDemo {


    public static void main(String[] args) {
//        System.out.println("输出：二进制转十进制"+twoToTen("01001110"));
//        System.out.println("输出：十进制换二进制"+tenToTwo(78));

//        System.out.println(tenToTwo(18));//10010
        System.out.println(tenToTwo(6));//01111   00010 - 2
        System.out.println(tenToTwo(4));//01111   00010 - 2
        test1();
    }

    public static int twoToTen(String str){
        System.out.println("输入"+str);
        int num = 0;
        int length = str.length();

        for(int i=length;i>0;i--){
            int item = Integer.parseInt(String.valueOf(str.charAt(length-i))); //取item 0 或 1
            num+=item * Math.pow(2,i-1); //item* 2的位数次幂
        }
        return num;

    }

    public static String tenToTwo(int num){
        System.out.println("输入"+num);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i =8;i>0;i--){
            if(num>=Math.pow(2,i-1)){
                stringBuilder.append(1);
                num-=Math.pow(2,i-1);
            }else{
                stringBuilder.append(0);
            }
        }
        return stringBuilder.toString();

    }

    public static void test1(){
        int a =6;
        int b=4;
        System.out.println("输出："+(a|=b));
    }

}
