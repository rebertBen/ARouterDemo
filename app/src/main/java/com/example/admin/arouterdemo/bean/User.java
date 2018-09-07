package com.example.admin.arouterdemo.bean;

import java.io.Serializable;

/**
 * @author bobo
 * <p>
 * function：
 * <p>
 * create_time：2018/8/14 13:50
 * update_by：
 * update_time:
 */
public class User implements Serializable{
    private String name;
    private String psw;

    public User(String name, String psw) {
        this.name = name;
        this.psw = psw;
    }
    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }
}
