package com.hb.websocketclientdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.hb.websocketclientdemo.model.LoginJson;
import com.hb.websocketclientdemo.model.loginAndSubscribe.LoginInfo;
import com.hb.websocketclientdemo.service.WebSocketControlService;
import com.hb.websocketclientdemo.service.model.WsStatus;
import com.hb.websocketclientdemo.service.model.core.WebSocketConnInfo;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
public class WebSocketService implements WebSocketControlService {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    private static final int CONNECTION_TIME_OUT = 5;

    @Autowired
    private List<WebSocketConnInfo> connInfos;

    @Autowired
    private OnMessageService onMessageService;


//    {
//        最初想着初始化，类加载的时候完成websocket的连接，
//        发现不太行，将websocket 初始化的部分搬入connect();
//    }

    private static CountDownLatch countDownLatch;

    @Override
    public void connect(List<WebSocketConnInfo> connInfos) {
        countDownLatch = new CountDownLatch(connInfos.size());

        for (WebSocketConnInfo connInfo : connInfos) {
            connInfo.setStatus(WsStatus.INIT);
            try {
                WebSocketClient client = new WebSocketClient(new URI(connInfo.getUrl())) {
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        logger.info("[" + connInfo.getAccount() + "] Connection Established, Login...");
                        // login
                        LoginJson loginJson = new LoginJson("login", new LoginInfo(connInfo.getAccount(), connInfo.getPassword()));
                        logger.info(JSON.toJSONString(loginJson));
                        this.send(JSON.toJSONString(loginJson));
                        // keepAlive
                        try {
                            this.getSocket().setKeepAlive(true);
                        } catch (SocketException e) {
                            logger.info("Account [" + connInfo.getAccount() + "] KeepAlive Error!");
                            e.printStackTrace();
                        }
                        connInfo.setStatus(WsStatus.OPEN);
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onMessage(String s) {
                        boolean isReadable = onMessageService.messageDispatch(s, connInfo);
                        if (!isReadable) {
                            logger.info("[" + connInfo.getAccount() + "] Unknown Msg: " + s);
                        }
                    }

                    @Override
                    public void onClose(int i, String s, boolean b) {
                        connInfo.setStatus(WsStatus.CLOSE);
                        logger.info("[" + connInfo.getAccount() + "]: Connection Closed!");
                    }

                    @Override
                    public void onError(Exception e) {
                        connInfo.setStatus(WsStatus.ERROR);
                        logger.info("[" + connInfo.getAccount() + "]: OnError");
                        e.printStackTrace();
                    }
                };
                client.connect();
                logger.info("Account [" + connInfo.getAccount() + "]: Connecting...");
                connInfo.setClient(client);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        try {
            countDownLatch.await(CONNECTION_TIME_OUT, TimeUnit.SECONDS);
            logger.info("WebsocketClient 尚未连接数：" + countDownLatch.getCount());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Waiting For All WebSocketClient Connection Established , CountDownLatch Error");
        }


    }


    @Override
    public void webSocketStart() {
        if (connInfos == null || connInfos.size() <= 0) {
            throw new IllegalArgumentException("No WebSocket-Client Configuration LoginInfo!");
        }
        this.connect(connInfos);
    }

    private void onOpenHandler(int clientNo) {
//        logger.info("WS" + clientNo + " onOpen: Connection Established");
//        wsClients.get(clientNo).send(JSON.toJSONString(loginInfos.get(clientNo)));
//        logger.info("Ws" + clientNo + " Logining...");
//        try {
//            wsClients.get(clientNo).getSocket().setKeepAlive(true);
//        } catch (SocketException e) {
//            logger.info("Ws" + clientNo + "setKeepAlive Exception");
//            e.printStackTrace();
//        }
//        wsClientsStatus.set(clientNo, WsStatus.OPEN);

    }

    // 前端控制台 手动添加 webSocket Client 并启动
//    public void startNewWebSocketClient(String url, String userId, String password, String subAccount) {
////        int clientNo = wsClients.size();
////        LoginJson loginInfo = new LoginJson("login", new LoginInfo(userId, password));
////        loginInfos.add(loginInfo);
////        accountList.add(clientNo, subAccount);
////        int instrumentListSize = wsInfos.getInstrumentIdList().size();
////        Topic[] topics = new Topic[instrumentListSize];
////        for (int i = 0; i < wsInfos.getInstrumentIdList().size(); i++) {
////            topics[i] = new Topic(subAccount, wsInfos.getInstrumentIdList().get(i));
////        }
////        SubscribeInfo subscribeInfo = new SubscribeInfo("subscribe", topics);
////        subscribeInfos.add(subscribeInfo);
////
////        wsClientsStatus.add(clientNo, WsStatus.INIT);
////        try {
////            WebSocketClient newClient = new WebSocketClient(new URI(url)) {
////                @Override
////                public void onOpen(ServerHandshake serverHandshake) {
////                    onOpenHandler(clientNo);
////                }
////
////                @Override
////                public void onMessage(String s) {
////                    boolean isReadable = onMessageService.messageDispatch(s, accountList.get(clientNo), clientNo);
////                    if (!isReadable)
//////                        logger.info("WebSocket_1 =====Unknown Message Received");
////                        logger.info("WS" + clientNo + " onMessage: Unknown Message Received");
////
////                }
////
////                @Override
////                public void onClose(int i, String s, boolean b) {
////                    logger.info("WS" + clientNo + " onClose: Connection Closed");
////                    // 重新连接
////                    wsClientsStatus.set(clientNo, WsStatus.CLOSE);
//////                    wsClients.get(clientNo).connect();
////                }
////
////                @Override
////                public void onError(Exception e) {
////                    e.printStackTrace();
////                    logger.info("WS" + clientNo + " onError: Websocket Exception");
////                    wsClientsStatus.set(clientNo, WsStatus.ERROR);
////                }
////            };
////            newClient.connect();
////            logger.info("WS" + clientNo + " :Connecting...");
////            wsClients.add(newClient);
////        } catch (Exception e) {
////            e.printStackTrace();
////            logger.info("Exception Occur in WsClient Init");
////        }
//    }


    @Override
    public void shutdown() {
        for (WebSocketConnInfo connInfo : connInfos) {
            WebSocketClient client = connInfo.getClient();
            WsStatus status = connInfo.getStatus();
            if (client != null
                    && status != null
                    && (status.getValue() * -1) / 1000 == 2
            ) {
                client.close();
                logger.info("[" + connInfo.getAccount() + "]: Closing ");
            } else {
                logger.info("[" + connInfo.getAccount() + "]: Not Initialized! Can't Be ShutDown");
            }
        }
    }

}
