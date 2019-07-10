package com.hb.websocketclientdemo.service;

import com.alibaba.fastjson.JSONObject;
import com.hb.websocketclientdemo.model.Topic;
import com.hb.websocketclientdemo.model.data.LoginResult;
import com.hb.websocketclientdemo.model.data.SubResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OnMessageService {

    @Autowired
    private LoginResult loginResult;

    @Autowired
    private SubResult subResult;


    //websocket client onMessage 方法后总转发，根据channel
    public boolean messageDispatch(String msg) {
        JSONObject msgJson = JSONObject.parseObject(msg);
        String channel = (String) msgJson.get("channel");
        if (channel.equals("login_result")) {
            //result 并非 Json 下面会报错
//            JSONObject result = (JSONObject) msgJson.get("result");
            loginResult.setResult((String) msgJson.get("result"));
            loginResult.setAccount((String) msgJson.get("account"));
            return true;
        }

        if (channel.equals("sub_result")) {
            subResult.setResult((String) msgJson.get("result"));
//            Topic[] topics = msgJson.get("topic",Topic.class);

        }

        return false;
    }


}
