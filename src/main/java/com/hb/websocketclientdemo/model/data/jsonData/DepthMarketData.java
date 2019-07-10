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
public class DepthMarketData {

    //    合约名
    private String instrumentId;
    //    交易日
    private String tradingDate;
    //    更新时间（秒）
    private String updateTime;
    //    更新时间毫秒数
    private String updateMillisec;
    //    最新价格
    private String lastPrice;
    //    调整价格
    private String adjustedPrice;
    //    合约成交量
    private String volume;
    //    合约成交额
    private String turnover;
    //    各买档价量
    private String bids;
    //    各卖档价量
    private String asks;

}
