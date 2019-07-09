package com.hb.websocketclientdemo.model.data;

/**
 * 【初始持仓】
 * 用户名	user_id
 * 合约名	instrument_id
 * 初始多头	long_pos
 * 初始空头	short_pos
 */
public class InitPosition {
    //    用户名
    private String userId;

    //    合约名
    private String instrumentId;

    //    初始多头
    private String longPos;
    //    初始空头
    private String shortPass;
}
