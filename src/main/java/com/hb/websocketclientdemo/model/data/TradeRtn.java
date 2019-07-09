package com.hb.websocketclientdemo.model.data;

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
public class TradeRtn {
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
    private String price;

    //    成交量
    private String volume;

    //    手续费
    private String fee;


}
