package com.hb.websocketclientdemo.service;

import org.java_websocket.client.WebSocketClient;

import java.util.List;

public interface WebSocketControlService {
    void connect();
    void login();
    void subscribe();
    void webSocketStart();
    void close();
    boolean isClientNull();
    boolean isClientOpen();
    boolean isClosed();
    List<WebSocketClient> getWsClients();

}
