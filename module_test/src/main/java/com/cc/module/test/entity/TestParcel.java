package com.cc.module.test.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class TestParcel implements Parcelable {
    private int age;
    private String name;

    public TestParcel(int age,String name){
        this.age = age;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    protected TestParcel(Parcel in) {
        age = in.readInt();
        name = in.readString();
    }

    public static final Creator<TestParcel> CREATOR = new Creator<TestParcel>() {
        @Override
        public TestParcel createFromParcel(Parcel in) {
            return new TestParcel(in);
        }

        @Override
        public TestParcel[] newArray(int size) {
            return new TestParcel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(age);
        parcel.writeString(name);
    }
}
