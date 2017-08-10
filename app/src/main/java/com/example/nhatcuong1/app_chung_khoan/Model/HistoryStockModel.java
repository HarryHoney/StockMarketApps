package com.example.nhatcuong1.app_chung_khoan.Model;

/**
 * Created by nhatcuong1 on 11/22/15.enduml
 * @startuml
 * UID --> SID
 * @enduml
 */
public class HistoryStockModel {
    private String SID;
    private int UID;
    private double oldprice;
    private double exchange;
    private double percentage;
    private int volume;
    private String time;
    private String method;

    public void setExchange(double exchange) {
        this.exchange = exchange;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setOldprice(double oldprice) {
        this.oldprice = oldprice;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getTime() {
        return time;
    }

    public double getExchange() {
        return exchange;
    }

    public double getOldprice() {
        return oldprice;
    }

    public double getPercentage() {
        return percentage;
    }

    public int getUID() {
        return UID;
    }

    public int getVolume() {
        return volume;
    }

    public String getMethod() {
        return method;
    }

    public String getSID() {
        return SID;
    }
}
