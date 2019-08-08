package com.hb.websocketclientdemo.service;

public interface WebSocketCallbackService {
    boolean messageDispatch(String msg, String subAccount,int wsClientNo);



}
