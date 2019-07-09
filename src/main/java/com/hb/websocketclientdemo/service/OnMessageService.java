package com.hb.websocketclientdemo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hb.websocketclientdemo.model.data.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OnMessageService {

    @Autowired
    private LoginResult loginResult;


    //websocket client onMessage 方法后总转发，根据channel
    public void messageDispatch(String msg){
        JSONObject msgJson = JSONObject.parseObject(msg);
        String channel = (String) msgJson.get("channel");
        if (channel.equals("login_result")){
            //result 并非 Json
//            JSONObject result = (JSONObject) msgJson.get("result");
//            loginResult = JSONObject.parseObject(result.toJSONString(),LoginResult.class);
        }
    }

}
