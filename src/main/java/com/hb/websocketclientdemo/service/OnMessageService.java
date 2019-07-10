package com.hb.websocketclientdemo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hb.websocketclientdemo.model.Info;
import com.hb.websocketclientdemo.model.Topic;
import com.hb.websocketclientdemo.model.data.dataVO.InstrumentData;
import com.hb.websocketclientdemo.model.data.dataVO.MonitorData;
import com.hb.websocketclientdemo.model.data.jsonData.InstrumentInfo;
import com.hb.websocketclientdemo.model.data.dataVO.LoginResult;
import com.hb.websocketclientdemo.model.data.dataVO.SubResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

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
        //
        BeanUtils.copyProperties(JSONObject.parseObject(object.toJSONString(), InstrumentInfo.class), instrumentInfo);
//        JSONObject.parseObject(object.toJSONString(), InstrumentInfo.class)
        String instrumentId = instrumentInfo.getInstrumentId();
        if (monitorData.getInstruments().containsKey(instrumentId)) {
            logger.info("InstrumentInfo Already Initialized");
        } else {
            monitorData.getInstruments().put(instrumentId,new InstrumentData());
        }
        InstrumentData instrumentData = monitorData.getInstruments().get(instrumentId);
        instrumentData.setInstrumentId(instrumentInfo.getInstrumentId());
        instrumentData.setContractMultiplier(Integer.valueOf(instrumentInfo.getContractMultiplier()));
        instrumentData.setPreSettlementPrice(Double.valueOf(instrumentInfo.getPreSettlementPrice()));
//        currentPrice = preSettlementPrice
        instrumentData.setCurrentPrice(instrumentData.getPreSettlementPrice());
        return true;
    }


}
