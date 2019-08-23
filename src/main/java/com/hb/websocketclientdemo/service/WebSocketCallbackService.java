package com.hb.websocketclientdemo.service;

import com.hb.websocketclientdemo.service.model.core.WebSocketConnInfo;

public interface WebSocketCallbackService {
    boolean messageDispatch(String msg, WebSocketConnInfo connInfo);
}
