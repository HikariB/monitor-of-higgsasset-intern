package com.hb.websocketclientdemo.model.data.jsonData;

import org.springframework.stereotype.Component;

/**
 * 【初始持仓】
 * 用户名	user_id
 * 合约名	instrument_id
 * 初始多头	long_pos
 * 初始空头	short_pos
 */
@Component
public class InitPosition {
    //    用户名
    private String userId;

    //    合约名
    private String instrumentId;

    //    初始多头
    private String longPos;
    //    初始空头
    private String shortPos;

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

    public String getLongPos() {
        return longPos;
    }

    public void setLongPos(String longPos) {
        this.longPos = longPos;
    }

    public String getShortPos() {
        return shortPos;
    }

    public void setShortPos(String shortPos) {
        this.shortPos = shortPos;
    }
}
