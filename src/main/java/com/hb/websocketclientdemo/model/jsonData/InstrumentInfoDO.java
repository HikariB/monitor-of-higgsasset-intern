package com.hb.websocketclientdemo.model.jsonData;

/**【合约信息】
 * 合约名	instrument_id
 * 交易所	exchange_id
 * 到期日（期货/期权）	expire_date
 * 合约乘数	contract_multiplier
 * 最小变动价位	price_tick
 * 执行价格（期权）	strike_price
 * 前收盘价	pre_close_price
 * 前结算价	pre_settlement_price
 * 涨停价	upper_limit_price
 * 跌停价	lower_limit_price
 */

public class InstrumentInfoDO {

    //    合约名
    private String instrumentId;
    //    交易所
    private String exchangeId;
    //    到期日（期货/期权）
    private String expireDate;
    //    合约乘数
    private int contractMultiplier;
    //    最小变动价位
    private double priceTick;
    //    执行价格
    private double strikePrice;
    //    前收盘价
    private double preClosePrice;
    //    前结算价
    private double preSettlementPrice;

    @Override
    public String toString() {
        return "InstrumentInfoDO{" +
                "instrumentId='" + instrumentId + '\'' +
                ", contractMultiplier=" + contractMultiplier +
                ", preSettlementPrice=" + preSettlementPrice +
                '}';
    }

    //    涨停价
    private double upperLimitPrice;
    //    跌停价
    private double lowerLimitPrice;

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public int getContractMultiplier() {
        return contractMultiplier;
    }

    public void setContractMultiplier(int contractMultiplier) {
        this.contractMultiplier = contractMultiplier;
    }

    public double getPriceTick() {
        return priceTick;
    }

    public void setPriceTick(double priceTick) {
        this.priceTick = priceTick;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public double getPreClosePrice() {
        return preClosePrice;
    }

    public void setPreClosePrice(double preClosePrice) {
        this.preClosePrice = preClosePrice;
    }

    public double getPreSettlementPrice() {
        return preSettlementPrice;
    }

    public void setPreSettlementPrice(double preSettlementPrice) {
        this.preSettlementPrice = preSettlementPrice;
    }

    public double getUpperLimitPrice() {
        return upperLimitPrice;
    }

    public void setUpperLimitPrice(double upperLimitPrice) {
        this.upperLimitPrice = upperLimitPrice;
    }

    public double getLowerLimitPrice() {
        return lowerLimitPrice;
    }

    public void setLowerLimitPrice(double lowerLimitPrice) {
        this.lowerLimitPrice = lowerLimitPrice;
    }
}
