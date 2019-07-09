package com.hb.websocketclientdemo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.websocketclientdemo.model.Info;
import com.hb.websocketclientdemo.model.LoginInfo;
import com.hb.websocketclientdemo.model.SubscribeInfo;
import com.hb.websocketclientdemo.model.Topic;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class WebSocketService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OnMessageService onMessageService;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    private WebSocketClient wsClient=null;

    //    ws://10.12.226.66:8085/socket
//    ws://114.55.210.206:9999/
//    new Draft_6455()
    @Value("${WebSocketClient.url}")
    private String websocketUrl;

    {
        //最初想着初始化，类加载的时候完成websocket的连接，
        //发现不太行，将websocket 初始化的部分搬入connect();
    }

    public void connect() {
//        if (wsClient == null) {
        try {
            wsClient = new WebSocketClient(new URI(this.websocketUrl)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    logger.info("=====Websocket Connection Established");
                }

                @Override
                public void onMessage(String s) {
                    logger.info("Received:" + s);
//                    objectMapper.
                    onMessageService.messageDispatch(s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    logger.info("=====Websocket Connection Closed");
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    logger.info("Error =====Websocket Connection Failed!");
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
//        }
        wsClient.connect();
        logger.info("=====Connecting");
        while (!wsClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)) ;
    }

    public void login(String userId, String password) throws JsonProcessingException {
        Info info = new Info();
        info.setUser_id(userId);
        info.setPassword(password);
        LoginInfo loginInfo = new LoginInfo("login", info);
        wsClient.send(objectMapper.writeValueAsString(loginInfo));
        logger.info("=====Logining...");
    }

    public void subscribe(Topic[] topics) throws JsonProcessingException {
        SubscribeInfo subscribeInfo = new SubscribeInfo("subscribe", topics);
        wsClient.send(objectMapper.writeValueAsString(subscribeInfo));
        logger.info("=====Subscribing...");
    }

    public void close() {
        wsClient.close();
        logger.info("=====Closing...");
    }

    public WebSocketClient getWsClient() {
        return wsClient;
    }

    public void setWsClient(WebSocketClient wsClient) {
        this.wsClient = wsClient;
    }

}
