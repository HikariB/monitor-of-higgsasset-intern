package com.hb.websocketclientdemo.model.data.jsonData;

import org.springframework.stereotype.Component;

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
@Component
public class InstrumentInfo {

    //    合约名
    private String instrumentId;
    //    交易所
    private String exchangeId;
    //    到期日（期货/期权）
    private String expireDate;
    //    合约乘数
    private String contractMultiplier;
    //    最小变动价位
    private String priceTick;
    //    执行价格
    private String strikePrice;
    //    前收盘价
    private String preClosePrice;
    //    前结算价
    private String preSettlementPrice;
    //    涨停价
    private String upperLimitPrice;
    //    跌停价
    private String lowerLimitPrice;

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

    public String getContractMultiplier() {
        return contractMultiplier;
    }

    public void setContractMultiplier(String contractMultiplier) {
        this.contractMultiplier = contractMultiplier;
    }

    public String getPriceTick() {
        return priceTick;
    }

    public void setPriceTick(String priceTick) {
        this.priceTick = priceTick;
    }

    public String getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(String strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getPreClosePrice() {
        return preClosePrice;
    }

    public void setPreClosePrice(String preClosePrice) {
        this.preClosePrice = preClosePrice;
    }

    public String getPreSettlementPrice() {
        return preSettlementPrice;
    }

    public void setPreSettlementPrice(String preSettlementPrice) {
        this.preSettlementPrice = preSettlementPrice;
    }

    public String getUpperLimitPrice() {
        return upperLimitPrice;
    }

    public void setUpperLimitPrice(String upperLimitPrice) {
        this.upperLimitPrice = upperLimitPrice;
    }

    public String getLowerLimitPrice() {
        return lowerLimitPrice;
    }

    public void setLowerLimitPrice(String lowerLimitPrice) {
        this.lowerLimitPrice = lowerLimitPrice;
    }
}
