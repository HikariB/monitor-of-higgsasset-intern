package com.hb.websocketclientdemo.service;

import com.alibaba.fastjson.JSON;
import com.hb.websocketclientdemo.model.LoginInfo;
import com.hb.websocketclientdemo.model.SubscribeInfo;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class WebSocketService {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    private WebSocketClient wsClient = null;
    private WebSocketClient wsClient2 = null;
    private WebSocketClient wsClient3 = null;
    private WebSocketClient wsClient4 = null;

    @Autowired
    private OnMessageService onMessageService;

    @Resource(name = "LoginInfoWS1")
    private LoginInfo loginInfo;

    @Resource(name = "LoginInfoWS2")
    private LoginInfo loginInfo2;

    @Resource(name = "LoginInfoWS3")
    private LoginInfo loginInfo3;

    @Resource(name = "LoginInfoWS4")
    private LoginInfo loginInfo4;

    @Resource(name = "SubscribeInfoWS1")
    private SubscribeInfo subscribeInfo;

    @Resource(name = "SubscribeInfoWS2")
    private SubscribeInfo subscribeInfo2;

    @Resource(name = "SubscribeInfoWS3")
    private SubscribeInfo subscribeInfo3;

    @Resource(name = "SubscribeInfoWS4")
    private SubscribeInfo subscribeInfo4;

    //    ws://10.12.226.66:8085/socket
//    ws://114.55.210.206:9999/
//    new Draft_6455()
    @Value("${WebSocketClient.url1}")
    private String websocketUrl;

    @Value("${WebSocketClient.url2}")
    private String websocketUrl_2;

    @Value("${WebSocketClient.url3}")
    private String websocketUrl_3;

    @Value("${WebSocketClient.url4}")
    private String websocketUrl_4;

    @Value("${subscribe.account1}")
    private String subAccount;

    @Value("${subscribe.account2}")
    private String subAccount2;

    @Value("${subscribe.account3}")
    private String subAccount3;

    @Value("${subscribe.account4}")
    private String subAccount4;

    {
        //最初想着初始化，类加载的时候完成websocket的连接，
        //发现不太行，将websocket 初始化的部分搬入connect();
    }

    public void connect() {
        try {
            wsClient = new WebSocketClient(new URI(this.websocketUrl)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    logger.info("WebSocket_1=====Connection Established");
                }

                @Override
                public void onMessage(String s) {
                    logger.info("WS1 Received:" + s);
                    boolean isReadable = onMessageService.messageDispatch(s, subAccount);
                    if (!isReadable)
                        logger.info("WebSocket_1 =====Unknown Message Received");

                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    logger.info("WebSocket_1 =====Websocket Connection Closed");
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    logger.info("WebSocket_1 Error =====Websocket Connection Failed!");
                }
            };

            wsClient2 = new WebSocketClient(new URI(this.websocketUrl_2)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    logger.info("WebSocket_2=====Connection Established");
                }

                @Override
                public void onMessage(String s) {
                    logger.info("WS2 Received:" + s);
                    boolean isReadable = onMessageService.messageDispatch(s, subAccount2);
                    if (!isReadable)
                        logger.info("WebSocket_2 =====Unknown Message Received");

                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    logger.info("WebSocket_2 =====Websocket Connection Closed");

                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    logger.info("WebSocket_2 Error =====Websocket Connection Failed!");
                }
            };

            wsClient3 = new WebSocketClient(new URI(this.websocketUrl_3)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    logger.info("WebSocket_3=====Connection Established");
                }

                @Override
                public void onMessage(String s) {
                    logger.info("WS3 Received:" + s);
                    boolean isReadable = onMessageService.messageDispatch(s, subAccount3);
                    if (!isReadable)
                        logger.info("WebSocket_3 =====Unknown Message Received");
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    logger.info("WebSocket_3 =====Websocket Connection Closed");
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    logger.info("WebSocket_3 Error =====Websocket Connection Failed!");
                }
            };

            wsClient4 = new WebSocketClient(new URI(this.websocketUrl_4)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    logger.info("WebSocket_4=====Connection Established");
                }

                @Override
                public void onMessage(String s) {
                    logger.info("WS4 Received:" + s);
                    boolean isReadable = onMessageService.messageDispatch(s, subAccount4);
                    if (!isReadable)
                        logger.info("WebSocket_4 =====Unknown Message Received");
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    logger.info("WebSocket_4 =====Websocket Connection Closed");
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    logger.info("WebSocket_4 Error =====Websocket Connection Failed!");
                }
            };

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        wsClient.connect();
        wsClient2.connect();
        wsClient3.connect();
        wsClient4.connect();

        logger.info("WS1-2-3-4=====Connecting");
        while (!wsClient.getReadyState().equals(WebSocket.READYSTATE.OPEN) ||
                !wsClient2.getReadyState().equals(WebSocket.READYSTATE.OPEN) ||
                !wsClient3.getReadyState().equals(WebSocket.READYSTATE.OPEN) ||
                !wsClient4.getReadyState().equals(WebSocket.READYSTATE.OPEN)) ;

    }

    public void login() {
        wsClient.send(JSON.toJSONString(loginInfo));
        wsClient2.send(JSON.toJSONString(loginInfo2));
        wsClient3.send(JSON.toJSONString(loginInfo3));
        wsClient4.send(JSON.toJSONString(loginInfo4));
        logger.info("WS1-2-3-4=====Logining...");
    }

    public void subscribe() {
        wsClient.send(JSON.toJSONString(subscribeInfo));
        wsClient2.send(JSON.toJSONString(subscribeInfo2));
        wsClient3.send(JSON.toJSONString(subscribeInfo3));
        wsClient4.send(JSON.toJSONString(subscribeInfo4));
        logger.info("WS1-2-3-4=====Subscribing...");
    }


    public void close() {
        wsClient.close();
        wsClient2.close();
        wsClient3.close();
        wsClient4.close();
        logger.info("WS1-2-3-4=====Closing...");
    }


    public boolean isClientNull() {
        return wsClient == null || wsClient2 == null || wsClient3 == null || wsClient4 == null;
    }

    public boolean isClientOpen() {
        return wsClient.getReadyState().equals(WebSocket.READYSTATE.OPEN) &&
                wsClient2.getReadyState().equals(WebSocket.READYSTATE.OPEN) &&
                wsClient3.getReadyState().equals(WebSocket.READYSTATE.OPEN) &&
                wsClient4.getReadyState().equals(WebSocket.READYSTATE.OPEN);
    }

    public boolean isClosed() {
        return wsClient.isClosed() && wsClient2.isClosed() && wsClient3.isClosed() && wsClient4.isClosed();
    }


    public WebSocketClient getWsClient() {
        return wsClient;
    }

    public WebSocketClient getWsClient2() {
        return wsClient2;
    }

    public WebSocketClient getWsClient3() {
        return wsClient3;
    }

    public WebSocketClient getWsClient4() {
        return wsClient4;
    }


}
