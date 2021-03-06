package com.hb.websocketclientdemo.model.jsonReceivedToObj;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 【报单回报】
 * 用户名	user_id
 * 合约名	instrument_id
 * 委托单交易所编号	order_sys_id
 * 委托单本地编号	order_ref
 * 委托方向（0买，1卖）	direction
 * 开平仓标志（0开，1平）	offset_flag
 * 委托价格	price
 * 委托总量	total_volume
 * 已成交数量	traded_volume
 * 委托单状态	order_status
 */

//@JSONField(name = "自定义")
//    经测试，驼峰命名和下划线命名，似乎Fastjson 能够识别，所以可以不加
// 注意Json化的时候需要getter and setter

public class OrderRtnDO {
    //    用户名

    @JSONField(name = "user_id")
    private String userId;

    //    合约名
    @JSONField(name = "instrument_id")
    private String instrumentId;

    //    委托单交易所编号
    @JSONField(name = "order_sys_id")
    private String orderSysId;

    //    委托单本地编号
    @JSONField(name = "order_ref")
    private String orderRef;

    //    交易所成交编号
    //----
//    private String tradeId;

    //    委托方向
    //0：买 1：卖
    @JSONField(name = "direction")
    private String direction;

    //    开平仓标志
    //    0：开 1：平

    @JSONField(name = "offset_flag")
    private String offsetFlag;

    //    委托价格
    @JSONField(name = "price")
    private double price;

    //    委托总量
    @JSONField(name = "total_volume")
    private double totalVolume;

    //    已成交数量
    @JSONField(name = "traded_volume")
    private double tradedVolume;

    //    委托单状态
    //0: 全部成交 1：排队中 2：已撤销 3：未知状态
    @JSONField(name = "order_status")
    private String orderStatus;

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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderSysId(String orderSysId) {
        this.orderSysId = orderSysId;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
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

    public String getOrderSysId() {
        return orderSysId;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public double getTradedVolume() {
        return tradedVolume;
    }

    public void setTradedVolume(double tradedVolume) {
        this.tradedVolume = tradedVolume;
    }

    @Override
    public String toString() {
        return "OrderRtnDO{" +
                "userId='" + userId + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                ", orderSysId='" + orderSysId + '\'' +
                ", direction='" + direction + '\'' +
                ", offsetFlag='" + offsetFlag + '\'' +
                ", price=" + price +
                ", totalVolume=" + totalVolume +
                ", tradedVolume=" + tradedVolume +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
