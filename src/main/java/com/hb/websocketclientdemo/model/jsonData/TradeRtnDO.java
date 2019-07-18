package com.hb.websocketclientdemo.model.jsonData;

/**【成交回报】
 * 用户名	user_id
 * 合约名	instrument_id
 * 委托单交易所编号	order_sys_id
 * 委托单本地编号	order_ref
 * 交易所成交编号	trade_id
 * 委托方向（0买，1卖）	direction
 * 开平仓标志（0开，1平）	offset_flag
 * 成交价	price
 * 成交量	volume
 * 手续费	fee
 *
 */
public class TradeRtnDO {
    //    用户名
    private String userId;

    //    合约名
    private String instrumentId;

    //    委托单交易所编号
    private String orderSysId;

    //    委托单本地编号
    private String orderRef;

    //    交易所成交编号
    //----
    private String tradeId;

    //    委托方向
    //0：买 1：卖
    private String direction;

    //    开平仓标志
    //    0：开 1：平
    private String offsetFlag;

    //    委托价格
    private double price;

    //    成交量
    private double volume;

    //    手续费
    private double fee;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getOrderSysId() {
        return orderSysId;
    }

    public void setOrderSysId(String orderSysId) {
        this.orderSysId = orderSysId;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
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

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }


}
