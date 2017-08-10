package com.example.nhatcuong1.app_chung_khoan.Model;

import java.io.Serializable;

/**
 * Created by nhatcuong1 on 11/17/15.
 */
public class ShareModel implements Serializable{
    private String name;
    private String id;
    private double price;
    private double change;
    private String time;
    private double volume;
    private double high;
    private double open;
    private double low;
    public ShareModel(){

    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVolume() {
        return volume;
    }

    public double getPrice() {
        return price;
    }

    public double getChange() {
        return change;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getOpen() {
        return open;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
