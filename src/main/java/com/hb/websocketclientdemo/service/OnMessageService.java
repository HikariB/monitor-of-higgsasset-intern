package com.hb.websocketclientdemo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hb.websocketclientdemo.model.Info;
import com.hb.websocketclientdemo.model.Topic;
import com.hb.websocketclientdemo.model.data.dataVO.*;
import com.hb.websocketclientdemo.model.data.jsonData.InitPosition;
import com.hb.websocketclientdemo.model.data.jsonData.InstrumentInfo;
import com.hb.websocketclientdemo.model.data.jsonData.OrderRtn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class OnMessageService {

    private static final Logger logger = LoggerFactory.getLogger(OnMessageService.class);

    @Autowired
    private LoginResult loginResult;

    @Autowired
    private SubResult subResult;

    @Autowired
    private InstrumentInfo instrumentInfo;

    @Autowired
    private MonitorData monitorData;

    @Autowired
    private InitPosition initPosition;


    //websocket client onMessage 方法后总转发，根据channel
    public boolean messageDispatch(String msg) {
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
            return instrumentInfoHandler(msgJson);
        }
        if (channel.equals("init_position")) {
            return initPositionHandler(msgJson);
        }
        if (channel.equals("order_rtn")) {
            return orderRtnHandler(msgJson);
        }
        if (channel.equals("trade_rtn")) {
            return tradeRtnHandler(msgJson);
        }
        if (channel.equals("depth_marketdata")) {
            return depthMarketDataHandler(msgJson);
        }
        return false;
    }

    private boolean loginResultHandler(JSONObject msgJson) {
        //result 并非 Json 下面会报错
//            JSONObject result = (JSONObject) msgJson.get("result");
        loginResult.setResult((String) msgJson.get("result"));
        loginResult.setAccount((String) msgJson.get("account"));
        if (loginResult.getResult().equals("success")) {
            //do sth to subscribe
            return true;
        }
        return false;
    }

    private boolean subResultHandler(JSONObject msgJson) {
        subResult.setResult((String) msgJson.get("result"));
        if (subResult.getResult().equals("success")) {
            Object objectTopic = msgJson.get("topic");
//          判断为数组还是对象
            if (objectTopic instanceof JSONArray) {
                LinkedList<Topic> topics = (LinkedList<Topic>) JSONObject.parseArray(((JSONObject) objectTopic).toJSONString(), Topic.class);
                subResult.setTopics(topics);
            } else {
                Topic topic = JSONObject.parseObject(((JSONObject) objectTopic).toJSONString(), Topic.class);
                List<Topic> topics = new LinkedList<>();
                topics.add(topic);
                subResult.setTopics(topics);
            }
            return true;
        }
        return false;
    }

    private boolean instrumentInfoHandler(JSONObject msgJson) {
        JSONObject object = (JSONObject) msgJson.get("data");
        //使用 copyProperties 进行修改，“=”赋值无效
        BeanUtils.copyProperties(JSONObject.parseObject(object.toJSONString(), InstrumentInfo.class), instrumentInfo);
//        JSONObject.parseObject(object.toJSONString(), InstrumentInfo.class)
        String instrumentId = instrumentInfo.getInstrumentId();
        if (monitorData.getInstruments().containsKey(instrumentId)) {
            logger.info("Another InstrumentInfo, ****Already Initialized");
        } else {
            // 初始化 instruments，以及orders
            monitorData.getInstruments().put(instrumentId, new InstrumentData());
        }
        InstrumentData instrumentData = monitorData.getInstruments().get(instrumentId);
        instrumentData.setInstrumentId(instrumentInfo.getInstrumentId());
        instrumentData.setContractMultiplier(Integer.valueOf(instrumentInfo.getContractMultiplier()));
        instrumentData.setPreSettlementPrice(Double.valueOf(instrumentInfo.getPreSettlementPrice()));
//        currentPrice = preSettlementPrice
        instrumentData.setCurrentPrice(instrumentData.getPreSettlementPrice());
        return true;
    }

    private boolean initPositionHandler(JSONObject msgJson) {
        JSONObject object = (JSONObject) msgJson.get("data");
        BeanUtils.copyProperties(JSONObject.parseObject(object.toJSONString(), InitPosition.class), initPosition);
        InstrumentData instrumentData = monitorData.getInstruments().get(initPosition.getInstrumentId());
        instrumentData.setInitLongPosition(Integer.valueOf(initPosition.getLongPos()));
        instrumentData.setInitShortPosition(Integer.valueOf(initPosition.getShortPos()));
        return true;
    }

    private boolean orderRtnHandler(JSONObject msgJson) {
        JSONObject object = (JSONObject) msgJson.get("data");
        OrderRtn orderRtnJson = JSONObject.parseObject(object.toJSONString(), OrderRtn.class);
        // 若之前不存在 订单表 则新增
        if (!monitorData.getOrders().containsKey(orderRtnJson.getInstrumentId())) {
            Map<Integer, OrderData> orders = new HashMap<>();
            monitorData.getOrders().put(orderRtnJson.getInstrumentId(), orders);
        }
        Map<Integer, OrderData> orderInfo = monitorData.getOrders().get(orderRtnJson.getInstrumentId());
        InstrumentData instrumentData = monitorData.getInstruments().get(orderRtnJson.getInstrumentId());
        //根据订单唯一表示 order_sys_id，判断之前是否存在
        int orderSysId = Integer.valueOf(orderRtnJson.getOrderSysId().trim());
        if (orderInfo.containsKey(orderSysId)) {
            //若之前已存在 该订单
            if (orderRtnJson.getOrderStatus().equals("1")) {
                //委托单状态0为全部成交、1为排队中、2为已撤销、3为未知状态
                //
                orderInfo.get(orderSysId).setTradeVolume(Double.valueOf(orderRtnJson.getTradedVolume()));
            } else {
                orderInfo.remove(orderSysId);
                if (orderRtnJson.getOrderStatus().equals("2")) {
                    //取消的订单数 +1
                    instrumentData.addOrderCancelNum(1);
                }
            }
        } else {
            // 若不存在订单
            instrumentData.addOrderInsertNum(1);
            instrumentData.addOrderFee(1);
            instrumentData.addOrderVolume(Double.valueOf(orderRtnJson.getTotalVolume()));
            if (orderRtnJson.getOrderStatus().equals("1")) {
                OrderData newObj = convertFromOrderRtnToOrderData(orderRtnJson);
                orderInfo.put(orderSysId,newObj);
            }
        }
        //
        return true;
    }

    private boolean tradeRtnHandler(JSONObject msgJson) {
        return true;
    }

    private boolean depthMarketDataHandler(JSONObject msgJson) {
        return true;
    }

    private OrderData convertFromOrderRtnToOrderData(OrderRtn orderRtnJson) {
        OrderData newObj = new OrderData();
        BeanUtils.copyProperties(orderRtnJson, newObj);
        newObj.setOrderSysId(Integer.parseInt(orderRtnJson.getOrderSysId().trim()));
        newObj.setPrice(Double.parseDouble(orderRtnJson.getPrice()));
        newObj.setTotalVolume(Double.parseDouble(orderRtnJson.getTotalVolume()));
        newObj.setTradeVolume(Double.parseDouble(orderRtnJson.getTradedVolume()));
        return newObj;
    }


}
