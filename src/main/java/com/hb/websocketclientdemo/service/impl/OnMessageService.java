package com.hb.websocketclientdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hb.websocketclientdemo.model.SubscribeJson;
import com.hb.websocketclientdemo.model.jsonReceivedToObj.*;
import com.hb.websocketclientdemo.model.loginAndSubscribe.NewTopic;
import com.hb.websocketclientdemo.service.WebSocketCallbackService;
import com.hb.websocketclientdemo.service.model.*;
import com.hb.websocketclientdemo.service.model.base.InstrumentData;
import com.hb.websocketclientdemo.service.model.base.OrderData;
import com.hb.websocketclientdemo.service.model.core.AccountData;
import com.hb.websocketclientdemo.service.model.core.WebSocketConnInfo;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class OnMessageService implements WebSocketCallbackService {

    private static final Logger logger = LoggerFactory.getLogger(OnMessageService.class);

    private static int DELAY_MAX = 10;

    @Autowired
    private Map<String, AccountData> allData;

    // onMessage的调用，根据channel转发
    @Override
    public boolean messageDispatch(String msg, WebSocketConnInfo connInfo) {
        if (msg == null || msg.equals("")) return false;
        JSONObject msgJson = JSONObject.parseObject(msg);
        String channel = (String) msgJson.get("channel");

        if (channel == null || channel.equals("")) return false;

        if (channel.equals("login_result")) return loginResultHandler(msgJson, connInfo);

        if (channel.equals("sub_result")) return subResultHandler(msgJson, connInfo);

        if (channel.equals("instrument_info")) return instrumentInfoHandler(msgJson, connInfo);

        if (channel.equals("init_position")) return initPositionHandler(msgJson, connInfo);

        if (channel.equals("order_rtn")) return orderRtnHandler(msgJson, connInfo);

        if (channel.equals("trade_rtn")) return tradeRtnHandler(msgJson, connInfo);

        if (channel.equals("depth_marketdata")) return depthMarketDataHandler(msgJson, connInfo);

        return false;
    }

    private boolean loginResultHandler(JSONObject msgJson, WebSocketConnInfo connInfo) {
        boolean isLogin = msgJson.get("result").equals("success");
        connInfo.setLogin(isLogin);
        logger.info("loginResultHandler [" + msgJson.get("account") + "]: " + msgJson.get("result"));
        if (isLogin) {
            connInfo.setStatus(WsStatus.SUBSCRIBED);
            //Subscribe
            SubscribeJson subscribeInfo = new SubscribeJson("subscribe", connInfo.getTopics());
            logger.info(JSON.toJSONString(subscribeInfo));
            connInfo.getClient().send(JSON.toJSONString(subscribeInfo));
            logger.info("[" + connInfo.getAccount() + "]: Subscribing...");
            // 初始化 数据容器
            String account = connInfo.getAccount();
            if (!allData.containsKey(account)) {
                // 初始化 instruments orders
                allData.put(account, new AccountData(connInfo, AccountType.forType(connInfo.getAccountType())));
            }
            return true;
        }
        connInfo.setStatus(WsStatus.LOGIN);
        logger.error("[" + connInfo.getAccount() + "]: Not Subscribing...");
        return false;
    }

    private boolean subResultHandler(JSONObject msgJson, WebSocketConnInfo connInfo) {
        boolean isSubscribed = msgJson.get("result").equals("success");
        String topicJson = ((JSONObject) msgJson.get("topic")).toJSONString();
        String instrumentId = (JSONObject.parseObject(topicJson, NewTopic.class)).getInstrumentId();
        logger.info("[" + connInfo.getAccount() + ":" + instrumentId + "] subResultHandler: " + msgJson.get("result"));
        if (isSubscribed) {
            if (connInfo.getSubscribedInstrument() == null) {
                connInfo.setSubscribedInstrument(new ArrayList<>());
            }
            connInfo.getSubscribedInstrument().add(instrumentId);
        }
        return isSubscribed;
    }

    private boolean instrumentInfoHandler(JSONObject msgJson, WebSocketConnInfo connInfo) {
        String account = connInfo.getAccount();
        AccountData accountData = allData.get(account);
        JSONObject object = (JSONObject) msgJson.get("data");
        InstrumentInfoDO instrumentInfoDO = JSONObject.parseObject(object.toJSONString(), InstrumentInfoDO.class);
        logger.info("[" + account + "] instrumentInfoHandler: " + instrumentInfoDO.toString());

        String instrumentId = instrumentInfoDO.getInstrumentId();
        if (accountData.getInstruments().containsKey(instrumentId)) {
            logger.info("Duplicate InstrumentInfo for instrumentId: " + instrumentId);
        } else {
            accountData.getInstruments().put(instrumentId, new InstrumentData());
        }
        InstrumentData instrumentData = accountData.getInstruments().get(instrumentId);
        instrumentData.setInstrumentId(instrumentInfoDO.getInstrumentId());
        instrumentData.setContractMultiplier(instrumentInfoDO.getContractMultiplier());
        instrumentData.setPreSettlementPrice(instrumentInfoDO.getPreSettlementPrice());
        instrumentData.setCurrentPrice(instrumentData.getPreSettlementPrice());
        return true;
    }

    private boolean initPositionHandler(JSONObject msgJson, WebSocketConnInfo connInfo) {
        String account = connInfo.getAccount();
        AccountData accountData = allData.get(account);
        JSONObject object = (JSONObject) msgJson.get("data");
        InitPositionDO initPositionDO = JSONObject.parseObject(object.toJSONString(), InitPositionDO.class);
        logger.info("[" + account + "] initPositionHandler: " + initPositionDO.toString());
        InstrumentData instrumentData = accountData.getInstruments().get(initPositionDO.getInstrumentId());
        instrumentData.setInitLongPosition(initPositionDO.getLongPos());
        instrumentData.setCurrentLongPosition(initPositionDO.getLongPos());
        instrumentData.setInitShortPosition(initPositionDO.getShortPos());
        instrumentData.setCurrentShortPosition(initPositionDO.getShortPos());
        double initPositionCost = instrumentData.getPreSettlementPrice() * (initPositionDO.getLongPos() - initPositionDO.getShortPos());
        instrumentData.setPositionCost(initPositionCost);
        return true;
    }

    private boolean orderRtnHandler(JSONObject msgJson, WebSocketConnInfo connInfo) {
        JSONObject object = (JSONObject) msgJson.get("data");
        OrderRtnDO orderDO = JSONObject.parseObject(object.toJSONString(), OrderRtnDO.class);
        logger.info("orderRtnHandler  " + orderDO.toString());
        // 若之前不存在 订单表 则新增
        AccountData accountData = allData.get(connInfo.getAccount());
        if (!accountData.getOrders().containsKey(orderDO.getInstrumentId())) {
            Map<Integer, OrderData> orders = new HashMap<>();
            accountData.getOrders().put(orderDO.getInstrumentId(), orders);
        }
        Map<Integer, OrderData> ordersInfo = accountData.getOrders().get(orderDO.getInstrumentId());
        InstrumentData instrumentData = accountData.getInstruments().get(orderDO.getInstrumentId());

        //Check UpdateTime
        if (instrumentData.isMarketDataValid() && checkUpdateMarketDataValid(instrumentData.getUpdateTime())) {
            logger.info("[" + connInfo.getAccount() + "]:" + "OrderRtnHandler: 无市场行情，更新超时");
            instrumentData.setMarketDataValid(false);
        }

        //根据订单唯一表示 order_sys_id，判断之前是否存在
        int orderSysId = Integer.valueOf(orderDO.getOrderSysId().trim());
        if (ordersInfo.containsKey(orderSysId)) {
            //若之前已存在 该订单
            if (orderDO.getOrderStatus().equals(ORDER_STATUS.QUEUED.getValue())) {
                //委托单状态0为全部成交、1为排队中、2为已撤销、3为未知状态
                //
                ordersInfo.get(orderSysId).setTradeVolume(orderDO.getTradedVolume());
            } else {
                ordersInfo.remove(orderSysId);
                if (orderDO.getOrderStatus().equals(ORDER_STATUS.CANCELD.getValue())) {
                    //取消的订单数 +1
                    instrumentData.addOrderCancelNum(1);
                }
            }
        } else {
            // 若不存在订单
            instrumentData.addOrderInsertNum(1);
            //2019 08 29 添加逻辑当且合约名开头为为IF IH IC时，OrderFee增加，其余固定为0
            String instrumentId = instrumentData.getInstrumentId();
            if (instrumentId.startsWith("IF") || instrumentId.startsWith("IH") || instrumentId.startsWith("IC")){
                instrumentData.addOrderFee(1);
            }
            instrumentData.addOrderVolume(orderDO.getTotalVolume());
            if (orderDO.getOrderStatus().equals(ORDER_STATUS.QUEUED.getValue())) {
                OrderData newObj = convertFromOrderRtnDOToOrderData(orderDO);
                ordersInfo.put(orderSysId, newObj);
            }
        }
        //
        return true;
    }

    private boolean tradeRtnHandler(JSONObject msgJson, WebSocketConnInfo connInfo) {
        JSONObject object = (JSONObject) msgJson.get("data");
        TradeRtnDO tradeDO = JSONObject.parseObject(object.toJSONString(), TradeRtnDO.class);
        logger.info("tradeRtnHandler  " + object.toJSONString());
        AccountData accountData = allData.get(connInfo.getAccount());
        InstrumentData instrumentData = accountData.getInstruments().get(tradeDO.getInstrumentId());

        //Check UpdateTime
        if (instrumentData.isMarketDataValid() && checkUpdateMarketDataValid(instrumentData.getUpdateTime())) {
            logger.info("[" + connInfo.getAccount() + "]:" + "TradeRtnHandler: 无市场行情，更新超时");
            instrumentData.setMarketDataValid(false);
        }

        instrumentData.addTradeVolume(tradeDO.getVolume());
//        update position cost
        String direction = tradeDO.getDirection();
        double tradeNum = tradeDO.getVolume() * tradeDO.getPrice();

//        double newFee = tradeNum * instrumentData.getContractMultiplier() * instrumentData.getFeeRate();
//        instrumentData.addFee(newFee);
        // 20190822
        instrumentData.addFee(tradeDO.getFee());

        if (direction.equals(DIRECTION.BUY.getValue())) {
            instrumentData.addPositionCost(tradeNum);
        } else {
            instrumentData.addPositionCost(tradeNum * (-1.0));
        }
//        currentShortPosition and currentLongPosition
        String offFlag = tradeDO.getOffsetFlag();
        //20190829 offFlag 添加 CLOSE_DAY
        if (direction.equals(DIRECTION.BUY.getValue()) && offFlag.equals(OFFSET_FLAG.OPEN.getValue())) {
            instrumentData.addCurrentLongPosition(tradeDO.getVolume());
        } else if (direction.equals(DIRECTION.SELL.getValue()) && !offFlag.equals(OFFSET_FLAG.OPEN.getValue())) {
            instrumentData.addCurrentLongPosition(tradeDO.getVolume() * (-1.0));
        } else if (direction.equals(DIRECTION.SELL.getValue()) && offFlag.equals(OFFSET_FLAG.OPEN.getValue())) {
            instrumentData.addCurrentShortPosition(tradeDO.getVolume());
        } else if (direction.equals(DIRECTION.BUY.getValue()) && !offFlag.equals(OFFSET_FLAG.OPEN.getValue())) {
            instrumentData.addCurrentShortPosition(tradeDO.getVolume() * (-1.0));
        }
        return true;
    }

    private boolean depthMarketDataHandler(JSONObject msgJson, WebSocketConnInfo connInfo) {
        String account = connInfo.getAccount();
        AccountData accountData = allData.get(account);
        JSONObject object = (JSONObject) msgJson.get("data");
        logger.info("depthMarketDataHandler  " + account + " " + object.toJSONString());

        DepthMarketDataDO marketDataDO = JSONObject.parseObject(object.toJSONString(), DepthMarketDataDO.class);
        InstrumentData instrumentData = accountData.getInstruments().get(marketDataDO.getInstrumentId());

        //通过比较当前时间和上一次的更新时间，判断数据是否有效
        if (instrumentData.isMarketDataValid() && checkUpdateMarketDataValid(instrumentData.getUpdateTime())) {
            logger.info("[" + connInfo.getAccount() + "]:" + "DepthMarketDataHandler： 无市场行情，更新超时");
            instrumentData.setMarketDataValid(false);
        }
        //检查更新时间update_time，若更新时间是现在的10 sec以前，则不作处理
        //判断当前时间和当前更新时间
        long delay = getMDDelaySec(marketDataDO.getUpdateTime());
        instrumentData.setMDDelaySec(delay);
        if (delay >= DELAY_MAX) {
            instrumentData.setMarketDataValid(false);
            logger.info("[" + connInfo.getAccount() + "]:" + "DepthMarketDataHandler：市场行情延迟");
            // 2019 08 05
//            return false;
        }

        //更新 currentPrice，Volume 属于整体市场的
        // 检查报价是否合理
        double[][] bids = marketDataDO.getBids();
        double[][] asks = marketDataDO.getAsks();
        if (bids[0][1] == 0 || asks[0][1] == 0) {
            logger.info("[" + connInfo.getAccount() + "]:" + "DepthMarketDataHandler：市场行情：无效报价");
            instrumentData.setMarketDataValid(false);
            return false;
        }
        double marketPrice = 0.5 * (bids[0][0] + asks[0][0]);
        instrumentData.setCurrentPrice(marketPrice);
        instrumentData.setVolume(marketDataDO.getVolume());
        instrumentData.setUpdateTime(marketDataDO.getUpdateTime());
        instrumentData.setMarketDataValid(true);
        if (!instrumentData.isMarketDataInitialized()) {
            instrumentData.setMarketDataInitialized(true);
        }
        return true;
    }

    private OrderData convertFromOrderRtnDOToOrderData(OrderRtnDO orderDO) {
        OrderData newObj = new OrderData();
        BeanUtils.copyProperties(orderDO, newObj);
        newObj.setOrderSysId(Integer.parseInt(orderDO.getOrderSysId().trim()));
        return newObj;
    }

    private boolean checkUpdateMarketDataValid(String time) {
        DateTime now = new DateTime();
        DateTime updateTime = new LocalTime(time).toDateTimeToday();
        //now - updateTime
        int secDelta = (int) new Duration(updateTime, now).getStandardSeconds();
        if (secDelta < 0)
            logger.info("行情信息超前" + (-secDelta) + "s，时间错误");
        return (secDelta >= DELAY_MAX);
    }

    private long getMDDelaySec(String time) {
        DateTime now = new DateTime();
        DateTime updateTime = new LocalTime(time).toDateTimeToday();
        //now - updateTime
        long secDelta = new Duration(updateTime, now).getStandardSeconds();
        if (secDelta < 0)
            logger.info("行情信息超前" + (-secDelta) + "s，时间错误");
        return secDelta;
    }

    public static int getDelayMax() {
        return DELAY_MAX;
    }

    public static void setDelayMax(int delayMax) {
        DELAY_MAX = delayMax;
    }
}
