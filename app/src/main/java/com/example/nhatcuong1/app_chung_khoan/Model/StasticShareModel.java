package com.example.nhatcuong1.app_chung_khoan.Model;

/**
 * Created by nhatcuong1 on 12/3/15.
 */
public class StasticShareModel {
    private String name;
    private Double currentPrice;
    private Double percentage;
    private Double averagePrice;
    private int volume;
    private int totalVolume;

    public void setTotalVolume(int totalVolume) {
        this.totalVolume = totalVolume;
    }

    public int getTotalVolume() {
        return totalVolume;
    }

    public int getVolume() {
        return volume;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public Double getPercentage() {
        return percentage;
    }

    public String getName() {
        return name;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setName(String name) {
        this.name = name;
    }
}
