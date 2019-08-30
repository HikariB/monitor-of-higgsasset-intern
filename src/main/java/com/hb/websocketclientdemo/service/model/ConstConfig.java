package com.hb.websocketclientdemo.service.model;

public class ConstConfig {
    private double accountTotalProfitLimit;
    private double instrumentProfitLimit;
    private double orderCancelWarnRatio;
    private int orderCancelLimit;
    private int netPositionLimit;
    private int mDelaySecLimit;


    public double getAccountTotalProfitLimit() {
        return accountTotalProfitLimit;
    }

    public void setAccountTotalProfitLimit(double accountTotalProfitLimit) {
        this.accountTotalProfitLimit = accountTotalProfitLimit;
    }

    public double getInstrumentProfitLimit() {
        return instrumentProfitLimit;
    }

    public void setInstrumentProfitLimit(double instrumentProfitLimit) {
        this.instrumentProfitLimit = instrumentProfitLimit;
    }

    public double getOrderCancelWarnRatio() {
        return orderCancelWarnRatio;
    }

    public void setOrderCancelWarnRatio(double orderCancelWarnRatio) {
        this.orderCancelWarnRatio = orderCancelWarnRatio;
    }

    public int getOrderCancelLimit() {
        return orderCancelLimit;
    }

    public void setOrderCancelLimit(int orderCancelLimit) {
        this.orderCancelLimit = orderCancelLimit;
    }

    public int getNetPositionLimit() {
        return netPositionLimit;
    }

    public void setNetPositionLimit(int netPositionLimit) {
        this.netPositionLimit = netPositionLimit;
    }

    public int getmDelaySecLimit() {
        return mDelaySecLimit;
    }

    public void setmDelaySecLimit(int mDelaySecLimit) {
        this.mDelaySecLimit = mDelaySecLimit;
    }
}
