package com.hb.websocketclientdemo.model.data;

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
public class OrderRtn {
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
//    private String tradeId;

    //    委托方向
    //0：买 1：卖
    private String direction;

    //    开平仓标志
    //    0：开 1：平
    private String offsetFlag;

    //    委托价格
    private String price;

    //    委托总量
    private String totalVolume;

    //    已成交数量
    private String tradedVolume;

    //    委托单状态
    //0: 全部成交 1：排队中 2：已撤销 3：未知状态
    private String orderStatus;
}
