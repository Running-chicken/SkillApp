package com.cc.library.base.entity;

public class Person {

    public Person(){}

    public Person(String name,int age){
        this.name = name;
        this.age = age;
    }

    public Person(int id,String name,int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    private int id;
    private String name;
    private int age;

    public int getId() {
        return id;
    }

    public String getIdShow(){
        return id+"";
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAgeShow(){
        return age+"";
    }

}