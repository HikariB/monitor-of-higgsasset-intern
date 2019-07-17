package com.hb.websocketclientdemo.controller.viewObj;

public class AccountSummary {
    private String accountId;
    private int tradeVolumeSum;
    private double volumeRatio;
    private double positionCost;
    private double feeSum;
    private double orderFeeSum;
    private double profitSum;
    private double profitNonNetSum;

    public double getProfitNonNetSum() {
        return profitNonNetSum;
    }

    public void setProfitNonNetSum(double profitNonNetSum) {
        this.profitNonNetSum = profitNonNetSum;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getTradeVolumeSum() {
        return tradeVolumeSum;
    }

    public void setTradeVolumeSum(int tradeVolumeSum) {
        this.tradeVolumeSum = tradeVolumeSum;
    }

    public double getVolumeRatio() {
        return volumeRatio;
    }

    public void setVolumeRatio(double volumeRatio) {
        this.volumeRatio = volumeRatio;
    }

    public double getPositionCost() {
        return positionCost;
    }

    public void setPositionCost(double positionCost) {
        this.positionCost = positionCost;
    }

    public double getFeeSum() {
        return feeSum;
    }

    public void setFeeSum(double feeSum) {
        this.feeSum = feeSum;
    }

    public double getOrderFeeSum() {
        return orderFeeSum;
    }

    public void setOrderFeeSum(double orderFeeSum) {
        this.orderFeeSum = orderFeeSum;
    }

    public double getProfitSum() {
        return profitSum;
    }

    public void setProfitSum(double profitSum) {
        this.profitSum = profitSum;
    }
}
