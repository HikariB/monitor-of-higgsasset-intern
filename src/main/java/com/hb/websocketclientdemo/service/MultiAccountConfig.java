package com.hb.websocketclientdemo.service;

import com.hb.websocketclientdemo.service.model.core.AccountData;
import com.hb.websocketclientdemo.service.model.core.WebSocketConnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MultiAccountConfig {

    @Autowired
    private List<WebSocketConnInfo> connInfos;

    @Bean
    public Map<String, AccountData> getMultiAccountData() {
        return new HashMap<>();
    }


    /**
     * connInfoMap 和 connInfos 捆绑在一起
     * @return
     */
    @Bean
    public Map<String, WebSocketConnInfo> getConnInfoMap() {
        Map<String, WebSocketConnInfo> connInfoMap = new HashMap<>();
        for (WebSocketConnInfo connInfo : connInfos) {
            connInfoMap.put(connInfo.getAccount(), connInfo);
        }
        return connInfoMap;
    }


}
