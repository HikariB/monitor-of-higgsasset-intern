package com.hb.websocketclientdemo.service.model.base;

public class InstrumentData {

    public static double CANCEL_WARN_RATIO = 0.98;
    public static double PROFIT_LIMIT = -20000;
    public static int ORDER_CANCEL_LIMIT = 390;
    public static int NET_POSITION_LIMIT = 5;
    //instrumentInfo 初始化
    private String instrumentId;
    private int contractMultiplier;
    private double preSettlementPrice;

    //InitPosition 初始化
    private int initLongPosition;
    private int initShortPosition;

    private boolean isMarketDataInitialized;

    //计算得出 根据现有数据
    private double profit;
    private double currentValue;
    private int netPosition;
    private double profitNonNet;


    //更新
    //行情 更新
    private double currentPrice;

    // tradeRtn
    private int currentLongPosition;
    private int currentShortPosition;
    private double positionCost;
    private double fee;
    private int tradeVolume;


    //orderRtn
    private int orderInsertNum;
    private int orderCancelNum;
    private double orderFee;
    private int orderVolume;
    private int volume;

    private double feeRate;


    private boolean isMarketDataValid;
    private String updateTime;

    //LoginInfo Limit WARNING
    private boolean isCancelRatioWarn;
    private boolean isCancelNumWarn;
    private boolean isProfitWarn;
    private boolean isNetPositionWarn;
    private long MDDelaySec;


    public InstrumentData() {

        this.currentPrice = 0;
        this.instrumentId = "";
        this.currentLongPosition = 0;
        this.currentShortPosition = 0;
        this.positionCost = 0;
        this.fee = 0;
        this.orderFee = 0;
        this.orderInsertNum = 0;
        this.orderCancelNum = 0;
        this.orderVolume = 0;
        this.volume = 0;
        this.tradeVolume = 0;
        this.contractMultiplier = 0;
        this.initLongPosition = 0;
        this.initShortPosition = 0;
        this.preSettlementPrice = 0;
        this.isMarketDataInitialized = false;
        this.isMarketDataValid = false;
        this.feeRate = 0.000024;

        this.isCancelRatioWarn = false;
        this.isCancelNumWarn = false;
        this.isProfitWarn = false;
        this.isNetPositionWarn = false;
        this.MDDelaySec = -1;
    }

    public long getMDDelaySec() {
        return MDDelaySec;
    }

    public void setMDDelaySec(long MDDelaySec) {
        this.MDDelaySec = MDDelaySec;
    }

    public boolean isCancelRatioWarn() {
        return (double) (this.orderCancelNum / ORDER_CANCEL_LIMIT) > CANCEL_WARN_RATIO;
    }

    public boolean isCancelNumWarn() {
        return (this.orderCancelNum > ORDER_CANCEL_LIMIT);
    }

    public boolean isProfitWarn() {
        return (this.profit < PROFIT_LIMIT);
    }

    public boolean isNetPositionWarn() {
        return Math.abs(this.getNetPosition()) > NET_POSITION_LIMIT;
    }

    public void addOrderCancelNum(int num) {
        orderCancelNum += num;
    }

    public void addOrderInsertNum(int num) {
        orderInsertNum += num;
    }

    public void addOrderFee(double num) {
        orderFee += num;
    }

    public void addOrderVolume(double num) {
        orderVolume += num;
    }

    public void addFee(double num) {
        fee += num;
    }

    public void addTradeVolume(double num) {
        tradeVolume += num;
    }

    public void addPositionCost(double num) {
        positionCost += num;
    }

    public void addCurrentLongPosition(double num) {
        currentLongPosition += num;
    }

    public void addCurrentShortPosition(double num) {
        currentShortPosition += num;
    }

    public double getProfit() {
        return contractMultiplier * (getCurrentValue() - positionCost) - fee - orderFee;
    }

    public double getProfitNonNet() {
        return contractMultiplier * (currentPrice - positionCost);
    }

    public double getCurrentValue() {
        return getCurrentPrice() * (currentLongPosition - currentShortPosition);
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getNetPosition() {
        return currentLongPosition - currentShortPosition;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public int getCurrentLongPosition() {
        return currentLongPosition;
    }

    public void setCurrentLongPosition(int currentLongPosition) {
        this.currentLongPosition = currentLongPosition;
    }

    public int getCurrentShortPosition() {
        return currentShortPosition;
    }

    public void setCurrentShortPosition(int currentShortPosition) {
        this.currentShortPosition = currentShortPosition;
    }

    public double getPositionCost() {
        return positionCost;
    }

    public void setPositionCost(double positionCost) {
        this.positionCost = positionCost;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getOrderFee() {
        return orderFee;
    }

    public void setOrderFee(double orderFee) {
        this.orderFee = orderFee;
    }

    public int getOrderInsertNum() {
        return orderInsertNum;
    }

    public void setOrderInsertNum(int orderInsertNum) {
        this.orderInsertNum = orderInsertNum;
    }

    public int getOrderCancelNum() {
        return orderCancelNum;
    }

    public void setOrderCancelNum(int orderCancelNum) {
        this.orderCancelNum = orderCancelNum;
    }

    public int getOrderVolume() {
        return orderVolume;
    }

    public void setOrderVolume(int orderVolume) {
        this.orderVolume = orderVolume;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getTradeVolume() {
        return tradeVolume;
    }

    public void setTradeVolume(int tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    public int getContractMultiplier() {
        return contractMultiplier;
    }

    public void setContractMultiplier(int contractMultiplier) {
        this.contractMultiplier = contractMultiplier;
    }

    public int getInitLongPosition() {
        return initLongPosition;
    }

    public void setInitLongPosition(int initLongPosition) {
        this.initLongPosition = initLongPosition;
    }

    public int getInitShortPosition() {
        return initShortPosition;
    }

    public void setInitShortPosition(int initShortPosition) {
        this.initShortPosition = initShortPosition;
    }

    public double getPreSettlementPrice() {
        return preSettlementPrice;
    }

    public void setPreSettlementPrice(double preSettlementPrice) {
        this.preSettlementPrice = preSettlementPrice;
    }

    public boolean isMarketDataInitialized() {
        return isMarketDataInitialized;
    }

    public void setMarketDataInitialized(boolean marketDataInitialized) {
        isMarketDataInitialized = marketDataInitialized;
    }

    public double getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(double feeRate) {
        this.feeRate = feeRate;
    }

    public boolean isMarketDataValid() {
        return isMarketDataValid;
    }

    public void setMarketDataValid(boolean marketDataValid) {
        isMarketDataValid = marketDataValid;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public static double getCancelWarnRatio() {
        return CANCEL_WARN_RATIO;
    }

    public static void setCancelWarnRatio(double cancelWarnRatio) {
        CANCEL_WARN_RATIO = cancelWarnRatio;
    }

    public static double getProfitLimit() {
        return PROFIT_LIMIT;
    }

    public static void setProfitLimit(double profitLimit) {
        PROFIT_LIMIT = profitLimit;
    }

    public static int getOrderCancelLimit() {
        return ORDER_CANCEL_LIMIT;
    }

    public static void setOrderCancelLimit(int orderCancelLimit) {
        ORDER_CANCEL_LIMIT = orderCancelLimit;
    }

    public static int getNetPositionLimit() {
        return NET_POSITION_LIMIT;
    }

    public static void setNetPositionLimit(int netPositionLimit) {
        NET_POSITION_LIMIT = netPositionLimit;
    }
}
