package com.cc.module.test.entity.javatest;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TestEquals {

    public static void main(String[] args) {
        Person person = new Person("cc");
        Person person2 = new Person("cc");

        Map<Person,String> map = new HashMap<>();
        map.put(person,person.name);
        map.put(person2,person2.name);

        System.out.println("结果是："+ map.size());
    }





    static class Person {
        String name;

        Person(String name){
            this.name = name;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if(this==obj){
                return true;
            }
            if(obj==null || getClass()!= obj.getClass()){
                return false;
            }
            return name.equals(((Person) obj).name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

}
