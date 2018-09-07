package com.example.admin.arouterdemo;

import android.content.Context;

import com.alibaba.android.arouter.facade.service.SerializationService;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * @author bobo
 * <p>
 * function：
 * <p>
 * create_time：2018/7/20 15:12
 * update_by：
 * update_time:
 */
public class Person implements Serializable{
    private String name;
    private int age;
    private float grade;

    public Person(){

    }

    public Person(String name, int age, float grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
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

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", grade=" + grade +
                '}';
    }

}
