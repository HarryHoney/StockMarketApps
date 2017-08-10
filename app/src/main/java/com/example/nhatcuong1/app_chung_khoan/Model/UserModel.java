package com.example.nhatcuong1.app_chung_khoan.Model;

import java.io.Serializable;

/**
 * Created by nhatcuong1 on 11/18/15.
 */
public class UserModel implements Serializable{
    private int id;
    private String password;
    private String username;
    private double money;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
