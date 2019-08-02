package com.hb.websocketclientdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.hb.websocketclientdemo.model.LoginInfo;
import com.hb.websocketclientdemo.model.SubscribeInfo;
import com.hb.websocketclientdemo.service.WSServerInfoConfig;
import com.hb.websocketclientdemo.service.WebSocketControlService;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class WebSocketService implements WebSocketControlService {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    @Autowired
    private  WSServerInfoConfig wsInfos;

    private List<WebSocketClient> wsClients = new ArrayList<>();

    @Autowired
    private List<LoginInfo> loginInfos;

    @Autowired
    private List<SubscribeInfo> subscribeInfos;

    @Autowired
    private OnMessageService onMessageService;

    @Resource(name = "AccountList")
    private List<String> accountList;

    private static CountDownLatch countDownLatch;

    {
        //最初想着初始化，类加载的时候完成websocket的连接，
        //发现不太行，将websocket 初始化的部分搬入connect();
    }

    @Override
    public void connect() {

        countDownLatch = new CountDownLatch(wsInfos.getUrlList().size());


        //根据URL 生成WSClient
        for (int i = 0; i < wsInfos.getUrlList().size(); i++) {
            int finalI = i;
            try {
                WebSocketClient webSocketClient = new WebSocketClient(new URI(wsInfos.getUrlList().get(finalI))) {
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        logger.info("WS" + finalI + " onOpen: Connection Established");
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onMessage(String s) {
                        boolean isReadable = onMessageService.messageDispatch(s, accountList.get(finalI));
                        if (!isReadable)
//                        logger.info("WebSocket_1 =====Unknown Message Received");
                            logger.info("WS" + finalI + " onMessage: Unknown Message Received");
                    }

                    @Override
                    public void onClose(int i, String s, boolean b) {
                        logger.info("WS" + finalI + " onClose: Connection Closed");
                    }

                    @Override
                    public void onError(Exception e) {
                        countDownLatch.countDown();
                        e.printStackTrace();
                        logger.info("WS" + finalI + " onError: Websocket Connection Failed!");
                        wsClients.remove(this);
                        logger.info("Remove WsClient "+finalI);
                    }
                };
                webSocketClient.connect();
                logger.info("WS" + i + " :Connecting");
                wsClients.add(webSocketClient);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("Exception Occur when in WSClient init");
            }
        }
        try {
            countDownLatch.await();
        } catch (Exception e){
            e.printStackTrace();
            logger.info("Waiting For All WebSocketClient Connection Established , CountDownLatch Error");
        }


    }

    @Override
    public void login() {
        for (int i = 0; i < wsClients.size(); i++) {
            try {
                wsClients.get(i).send(JSON.toJSONString(loginInfos.get(i)));
                logger.info("Ws" + i + " Logining...");
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("Ws" + i + "LoginInfo Send Failed");
            }
        }
    }


    @Override
    public void subscribe() {
        for (int i = 0; i < wsClients.size(); i++) {
            try {
                wsClients.get(i).send(JSON.toJSONString(subscribeInfos.get(i)));
                logger.info("Ws" + i + " Subscribing...");
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("Ws" + i + "SubscribeInfo Send Failed");
            }
        }
    }

    @Override
    public void webSocketStart() {
        this.connect();
        for (int i = 0; i < wsClients.size(); i++) {
            try {
                wsClients.get(i).send(JSON.toJSONString(loginInfos.get(i)));
                logger.info("Ws" + i + " Logining...");
                wsClients.get(i).send(JSON.toJSONString(subscribeInfos.get(i)));
                logger.info("Ws" + i + " Subscribing...");
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("Ws" + i + " Start Failed");
            }
        }
    }


    @Override
    public void close() {
        for (int i = 0; i < wsClients.size(); i++) {
            wsClients.get(i).close();
            logger.info("Ws" + i + " Closing...");
        }
    }

    @Override
    public boolean isClientNull() {
        for (int i = 0; i < wsClients.size(); i++) {
            if (wsClients.get(i) == null)
                return true;
        }
        return false;
    }
//        return wsClient == null || wsClient2 == null || wsClient3 == null || wsClient4 == null;

    @Override
    public boolean isClientOpen() {
        for (int i = 0; i < wsClients.size(); i++) {
            if (!wsClients.get(i).getReadyState().equals(WebSocket.READYSTATE.OPEN))
                return false;
        }
        return true;
    }
//        return wsClient.getReadyState().equals(WebSocket.READYSTATE.OPEN) &&
//                wsClient2.getReadyState().equals(WebSocket.READYSTATE.OPEN) &&
//                wsClient3.getReadyState().equals(WebSocket.READYSTATE.OPEN) &&
//                wsClient4.getReadyState().equals(WebSocket.READYSTATE.OPEN);

    @Override
    public boolean isClosed() {
        for (int i = 0; i < wsClients.size(); i++) {
            if (!wsClients.get(i).isClosed())
                return false;
        }
        return true;
    }
//        return wsClient.isClosed() && wsClient2.isClosed() && wsClient3.isClosed() && wsClient4.isClosed();

    @Override
    public List<WebSocketClient> getWsClients() {
        return wsClients;
    }
}
