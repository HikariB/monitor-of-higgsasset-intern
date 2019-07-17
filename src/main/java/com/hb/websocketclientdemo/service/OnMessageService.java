package com.hb.websocketclientdemo.service;

import com.alibaba.fastjson.JSONObject;
import com.hb.websocketclientdemo.model.Topic;
import com.hb.websocketclientdemo.model.data.jsonData.*;
import com.hb.websocketclientdemo.service.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OnMessageService {

    private static final Logger logger = LoggerFactory.getLogger(OnMessageService.class);

    @Autowired
    private MultiAccountMonitorData multiAccountData;

    //websocket client onMessage 方法后总转发，根据channel
    public boolean messageDispatch(String msg, String subAccount) {
        if (msg == null || msg.equals(""))
            return false;
        JSONObject msgJson = JSONObject.parseObject(msg);
        String channel = (String) msgJson.get("channel");
        if (channel == null || channel.equals(""))
            return false;
        if (channel.equals("login_result")) {
            return loginResultHandler(msgJson);
        }
        if (channel.equals("sub_result")) {
            return subResultHandler(msgJson);
        }
        if (channel.equals("instrument_info")) {
            return instrumentInfoHandler(msgJson, subAccount);
        }
        if (channel.equals("init_position")) {
            return initPositionHandler(msgJson, subAccount);
        }
        if (channel.equals("order_rtn")) {
            return orderRtnHandler(msgJson);
        }
        if (channel.equals("trade_rtn")) {
            return tradeRtnHandler(msgJson);
        }
        if (channel.equals("depth_marketdata")) {
            return depthMarketDataHandler(msgJson, subAccount);
        }
        return false;
    }

    private boolean loginResultHandler(JSONObject msgJson) {

        //result 并非 Json 下面会报错
//            JSONObject result = (JSONObject) msgJson.get("result");
        LoginResult loginResult = multiAccountData.getLoginResult();
        loginResult.setResult((String) msgJson.get("result"));
        loginResult.getAccount().add((String) msgJson.get("account"));
        logger.info("loginResultHandler:" + msgJson.get("account") + ":" + msgJson.get("result"));
        if (loginResult.getResult().equals("success")) {
            //do sth to subscribe
            return true;
        }
        logger.error("loginResultHandler: Login Failed");
        return false;
    }

    private boolean subResultHandler(JSONObject msgJson) {
        SubResult subResult = multiAccountData.getSubResult();
        subResult.setResult((String) msgJson.get("result"));
        JSONObject objectTopic = (JSONObject) msgJson.get("topic");
        logger.info("subResultHandler:" + objectTopic.toJSONString() + ":" + msgJson.get("result"));
        if (subResult.getResult().equals("success")) {
            Topic topic = JSONObject.parseObject(objectTopic.toJSONString(), Topic.class);
            subResult.getTopics().add(topic);
            return true;
        }
        logger.error("subResultHandler: Subscribe Failed");
        return false;
    }

    private boolean instrumentInfoHandler(JSONObject msgJson, String subAccount) {
        //检查是否已存在
        if (!multiAccountData.getAccountsInfo().containsKey(subAccount)) {
            multiAccountData.getAccountsInfo().put(subAccount, new MonitorData());
        }
        MonitorData monitorData = multiAccountData.getAccountsInfo().get(subAccount);

        JSONObject object = (JSONObject) msgJson.get("data");
        //使用 copyProperties 进行修改，“=”赋值无效
        InstrumentInfoDO instrumentInfoDO = JSONObject.parseObject(object.toJSONString(), InstrumentInfoDO.class);
        logger.info("instrumentInfoHandler  " + subAccount + ":" + instrumentInfoDO.toString());
//        BeanUtils.copyProperties(JSONObject.parseObject(object.toJSONString(), InstrumentInfoDO.class), instrumentInfoDO);
//        JSONObject.parseObject(object.toJSONString(), InstrumentInfo.class)
        String instrumentId = instrumentInfoDO.getInstrumentId();
        if (monitorData.getInstruments().containsKey(instrumentId)) {
            logger.info("Another InstrumentInfo, ****Already Initialized");
        } else {
            // 初始化 instruments，以及orders
            monitorData.getInstruments().put(instrumentId, new InstrumentData());
        }
        InstrumentData instrumentData = monitorData.getInstruments().get(instrumentId);
        instrumentData.setInstrumentId(instrumentInfoDO.getInstrumentId());
        instrumentData.setContractMultiplier(instrumentInfoDO.getContractMultiplier());
        instrumentData.setPreSettlementPrice(instrumentInfoDO.getPreSettlementPrice());
//        currentPrice = preSettlementPrice
        instrumentData.setCurrentPrice(instrumentData.getPreSettlementPrice());
        return true;
    }

    private boolean initPositionHandler(JSONObject msgJson, String subAccount) {

        MonitorData monitorData = multiAccountData.getAccountsInfo().get(subAccount);

        JSONObject object = (JSONObject) msgJson.get("data");
        InitPositionDO initPositionDO = JSONObject.parseObject(object.toJSONString(), InitPositionDO.class);
        logger.info("initPositionHandler  " + subAccount + ":" + initPositionDO.toString());
//        BeanUtils.copyProperties(JSONObject.parseObject(object.toJSONString(), InitPositionDO.class), initPosition);
        InstrumentData instrumentData = monitorData.getInstruments().get(initPositionDO.getInstrumentId());
        instrumentData.setInitLongPosition(initPositionDO.getLongPos());
        instrumentData.setCurrentLongPosition(initPositionDO.getLongPos());
        instrumentData.setInitShortPosition(initPositionDO.getShortPos());
        instrumentData.setCurrentShortPosition(initPositionDO.getShortPos());
        double initPositionCost = instrumentData.getPreSettlementPrice() * (initPositionDO.getLongPos() - initPositionDO.getShortPos());
        instrumentData.setPositionCost(initPositionCost);
        return true;
    }

    private boolean orderRtnHandler(JSONObject msgJson) {
        JSONObject object = (JSONObject) msgJson.get("data");
        OrderRtnDO orderDO = JSONObject.parseObject(object.toJSONString(), OrderRtnDO.class);
        logger.info("orderRtnHandler  " + orderDO.toString());
        // 若之前不存在 订单表 则新增
        MonitorData monitorData = multiAccountData.getAccountsInfo().get(orderDO.getUserId());
        if (!monitorData.getOrders().containsKey(orderDO.getInstrumentId())) {
            Map<Integer, OrderData> orders = new HashMap<>();
            monitorData.getOrders().put(orderDO.getInstrumentId(), orders);
        }
        Map<Integer, OrderData> ordersInfo = monitorData.getOrders().get(orderDO.getInstrumentId());
        InstrumentData instrumentData = monitorData.getInstruments().get(orderDO.getInstrumentId());
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
            instrumentData.addOrderFee(1);
            instrumentData.addOrderVolume(orderDO.getTotalVolume());
            if (orderDO.getOrderStatus().equals(ORDER_STATUS.QUEUED.getValue())) {
                OrderData newObj = convertFromOrderRtnDOToOrderData(orderDO);
                ordersInfo.put(orderSysId, newObj);
            }
        }
        //
        return true;
    }

    private boolean tradeRtnHandler(JSONObject msgJson) {
        JSONObject object = (JSONObject) msgJson.get("data");
        TradeRtnDO tradeDO = JSONObject.parseObject(object.toJSONString(), TradeRtnDO.class);
        logger.info("tradeRtnHandler  " + object.toJSONString());
        MonitorData monitorData = multiAccountData.getAccountsInfo().get(tradeDO.getUserId());
        InstrumentData instrumentData = monitorData.getInstruments().get(tradeDO.getInstrumentId());

        instrumentData.addTradeVolume(tradeDO.getVolume());
//        update position cost
        String direction = tradeDO.getDirection();
        double tradeNum = tradeDO.getVolume() * tradeDO.getPrice();
        double newFee = tradeNum * instrumentData.getContractMultiplier() * instrumentData.getFeeRate();
        instrumentData.addFee(newFee);

        if (direction.equals(DIRECTION.BUY.getValue())) {
            instrumentData.addPositionCost(tradeNum);
        } else {
            instrumentData.addPositionCost(tradeNum * (-1.0));
        }
//        currentShortPosition and currentLongPosition
        String offFlag = tradeDO.getOffsetFlag();
        if (direction.equals(DIRECTION.BUY.getValue()) && offFlag.equals(OFFSET_FLAG.OPEN.getValue())) {
            instrumentData.addCurrentLongPosition(tradeDO.getVolume());
        } else if (direction.equals(DIRECTION.SELL.getValue()) && offFlag.equals(OFFSET_FLAG.CLOSED.getValue())) {
            instrumentData.addCurrentLongPosition(tradeDO.getVolume() * (-1.0));
        } else if (direction.equals(DIRECTION.SELL.getValue()) && offFlag.equals(OFFSET_FLAG.OPEN.getValue())) {
            instrumentData.addCurrentShortPosition(tradeDO.getVolume());
        } else if (direction.equals(DIRECTION.BUY.getValue()) && offFlag.equals(OFFSET_FLAG.CLOSED.getValue())) {
            instrumentData.addCurrentShortPosition(tradeDO.getVolume() * (-1.0));
        }

        return true;
    }

    private boolean depthMarketDataHandler(JSONObject msgJson, String subAccount) {
        MonitorData monitorData = multiAccountData.getAccountsInfo().get(subAccount);
        JSONObject object = (JSONObject) msgJson.get("data");
        logger.info("depthMarketDataHandler  " + object.toJSONString());

        DepthMarketDataDO marketDataDO = JSONObject.parseObject(object.toJSONString(), DepthMarketDataDO.class);
        InstrumentData instrumentData = monitorData.getInstruments().get(marketDataDO.getInstrumentId());
        //检查更新时间update_time，若更新时间是现在的10 sec以前，则不作处理
        //

        //更新 currentPrice，Volume 属于整体市场的shux
        // 检查报价是否合理
        double[][] bids = marketDataDO.getBids();
        double[][] asks = marketDataDO.getAsks();
        if (bids[0][1] == 0 || asks[0][1] == 0) {
            logger.info("市场行情：无效报价");
            return false;
        }
        if (!instrumentData.isMarketDataInitialized())
            instrumentData.setMarketDataInitialized(true);
        double marketPrice = 0.5 * (bids[0][0] + asks[0][0]);
        instrumentData.setCurrentPrice(marketPrice);

        instrumentData.setVolume(marketDataDO.getVolume());
        return true;
    }

    private OrderData convertFromOrderRtnDOToOrderData(OrderRtnDO orderDO) {
        OrderData newObj = new OrderData();
        BeanUtils.copyProperties(orderDO, newObj);
        newObj.setOrderSysId(Integer.parseInt(orderDO.getOrderSysId().trim()));
        return newObj;
    }


}
