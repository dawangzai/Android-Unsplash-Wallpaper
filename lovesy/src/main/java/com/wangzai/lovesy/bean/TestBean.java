package com.wangzai.lovesy.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangzai on 2017/10/26
 */

public class TestBean implements Parcelable{
    private String name;
    private String age;

    public TestBean(String name, String age) {
        this.name = name;
        this.age = age;
    }

    protected TestBean(Parcel in) {
        name = in.readString();
        age = in.readString();
    }

    public static final Creator<TestBean> CREATOR = new Creator<TestBean>() {
        @Override
        public TestBean createFromParcel(Parcel in) {
            return new TestBean(in);
        }

        @Override
        public TestBean[] newArray(int size) {
            return new TestBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(age);
    }
}
