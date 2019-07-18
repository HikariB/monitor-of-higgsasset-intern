package com.hb.websocketclientdemo.model.jsonData;

/**
 * 【初始持仓】
 * 用户名	user_id
 * 合约名	instrument_id
 * 初始多头	long_pos
 * 初始空头	short_pos
 */

public class InitPositionDO {
    //    用户名
    private String userId;

    //    合约名
    private String instrumentId;

    //    初始多头
    private int longPos;
    //    初始空头
    private int shortPos;

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

    public int getLongPos() {
        return longPos;
    }

    public void setLongPos(int longPos) {
        this.longPos = longPos;
    }

    public int getShortPos() {
        return shortPos;
    }

    public void setShortPos(int shortPos) {
        this.shortPos = shortPos;
    }

    @Override
    public String toString() {
        return "InitPositionDO{" +
                "userId='" + userId + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                ", longPos=" + longPos +
                ", shortPos=" + shortPos +
                '}';
    }
}
