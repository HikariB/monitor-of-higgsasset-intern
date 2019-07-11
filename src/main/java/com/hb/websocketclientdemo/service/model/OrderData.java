package com.hb.websocketclientdemo.service.model;

public class OrderData {
    private int orderSysId;
    private String instrumentId;
    private String direction;
    private String offsetFlag;
    private double price;
    private double totalVolume;
    private double tradeVolume;
    private String insertTime;

    public OrderData() {
        this.orderSysId = 0;

        this.price = 0;
        this.totalVolume = 0;
        this.tradeVolume = 0;
        this.insertTime = "";
    }

    public int getOrderSysId() {
        return orderSysId;
    }

    public void setOrderSysId(int orderSysId) {
        this.orderSysId = orderSysId;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getOffsetFlag() {
        return offsetFlag;
    }

    public void setOffsetFlag(String offsetFlag) {
        this.offsetFlag = offsetFlag;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public double getTradeVolume() {
        return tradeVolume;
    }

    public void setTradeVolume(double tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }
}
