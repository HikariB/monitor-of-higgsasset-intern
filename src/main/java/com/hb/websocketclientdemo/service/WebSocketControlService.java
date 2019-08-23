package com.hb.websocketclientdemo.service;

import com.hb.websocketclientdemo.service.model.core.WebSocketConnInfo;

import java.util.List;

public interface WebSocketControlService {
    void connect(List<WebSocketConnInfo> connInfos);
    void webSocketStart();
    void shutdown();
}
