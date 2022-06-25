package com.cc.module.test.entity.testfather;

public class Son extends Father implements ShowInterface {

    public String name;
    private String address;
    public int age;
    private boolean isPerson;

    public Son(){
//        System.out.println("son no param constructor");
    }

    public Son(String name){
        this.name = name;
        System.out.println("this is one params");
    }

    public Son(String name,String address){
        this.name = name;
        this.address = address;
    }

    @Override
    public void sendFather() {
        System.out.println("this is son");
    }

    private int isSonPrivate(int pp,String haha){
        return 66;
    }

    public String getName(boolean isPerson){
        if(isPerson){
            return "cuican";
        }else{
            return "666";
        }
    }

    static{
        System.out.println("son static");
    }

    @Override
    public void showName() {

    }
}
