package com.hb.websocketclientdemo.model.data.jsonData;

/**
 * 【深度行情】
 * 合约名	instrument_id
 * 交易日	trading_date
 * 更新时间（秒）	update_time
 * 更新时间毫秒数	update_millisec
 * 最新价格	last_price
 * 调整价格	adjusted_price
 * 合约成交量	volume
 * 合约成交额	turnover
 * 各买档价量	bids
 * 各卖档价量	asks
 */
public class DepthMarketDataDO {

    //    合约名
    private String instrumentId;
    //    交易日
    private String tradingDate;
    //    更新时间（秒）
    private String updateTime;
    //    更新时间毫秒数
    private String updateMillisec;
    //    最新价格
    private double lastPrice;
    //    调整价格
    private double adjustedPrice;
    //    合约成交量
    private int volume;
    //    合约成交额
    private double turnover;
    //    各买档价量
    private double[][] bids;
    //    各卖档价量
    private double[][] asks;

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(String tradingDate) {
        this.tradingDate = tradingDate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateMillisec() {
        return updateMillisec;
    }

    public void setUpdateMillisec(String updateMillisec) {
        this.updateMillisec = updateMillisec;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public double getAdjustedPrice() {
        return adjustedPrice;
    }

    public void setAdjustedPrice(double adjustedPrice) {
        this.adjustedPrice = adjustedPrice;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public double[][] getBids() {
        return bids;
    }

    public void setBids(double[][] bids) {
        this.bids = bids;
    }

    public double[][] getAsks() {
        return asks;
    }

    public void setAsks(double[][] asks) {
        this.asks = asks;
    }
}
