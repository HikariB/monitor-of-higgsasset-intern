package com.hb.websocketclientdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.hb.websocketclientdemo.model.Info;
import com.hb.websocketclientdemo.model.LoginInfo;
import com.hb.websocketclientdemo.model.SubscribeInfo;
import com.hb.websocketclientdemo.model.Topic;
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
import java.net.SocketException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
public class WebSocketService implements WebSocketControlService {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    private static final int CONNECTION_TIME_OUT = 5;

    @Autowired
    private WSServerInfoConfig wsInfos;

    private List<WebSocketClient> wsClients = new ArrayList<>();

    private List<WsStatus> wsClientsStatus = new ArrayList<>();

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
            wsClientsStatus.add(i, WsStatus.INIT);
            int finalI = i;
            try {
                WebSocketClient webSocketClient = new WebSocketClient(new URI(wsInfos.getUrlList().get(finalI))) {
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        onOpenHandler(finalI);
                    }

                    @Override
                    public void onMessage(String s) {
                        boolean isReadable = onMessageService.messageDispatch(s, accountList.get(finalI), finalI);
                        if (!isReadable)
//                        logger.info("WebSocket_1 =====Unknown Message Received");
                            logger.info("WS" + finalI + " onMessage: Unknown Message Received");
                    }

                    @Override
                    public void onClose(int i, String s, boolean b) {
                        logger.info("WS" + finalI + " onClose: Connection Closed");
                        // 重新连接
                        wsClientsStatus.set(finalI, WsStatus.CLOSE);
//                        wsClients.get(finalI).connect();
                    }

                    @Override
                    public void onError(Exception e) {
//                        countDownLatch.countDown();
                        e.printStackTrace();
                        logger.info("WS" + finalI + " onError: Websocket Exception");
                        wsClientsStatus.set(finalI, WsStatus.ERROR);
//                        wsClients.remove(this);
//                        logger.info("Remove WsClient " + finalI);
                    }
                };
                webSocketClient.connect();
                logger.info("WS" + i + " :Connecting...");

                wsClients.add(webSocketClient);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("Exception Occur in WSClient Init");
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
    public void login() {
        for (int i = 0; i < wsClients.size(); i++) {
            try {
                if (wsClients.get(i).getReadyState() != WebSocket.READYSTATE.OPEN) {
                    logger.info("WebSocket Login Skip Ws" + i);
                    continue;
                }
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
                if (wsClients.get(i).getReadyState() != WebSocket.READYSTATE.OPEN) {
                    logger.info("WebSocket Subscribe: Skip Ws" + i);
                    continue;
                }
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
        // 考虑到发送信息必须是建立连接后才能发送的，所以需要判断getReadyState
        // 更简单而符合逻辑的做法是,把登入请求放在OnOpen中,即连接的Callback 函数中
//        for (int i = 0; i < wsClients.size(); i++) {
//            try {
//                if (wsClients.get(i).getReadyState() != WebSocket.READYSTATE.OPEN) {
//                    logger.info("WebSocket Start: Skip Ws"+i);
//                    continue;
//                }
//                wsClients.get(i).send(JSON.toJSONString(loginInfos.get(i)));
//                logger.info("Ws" + i + " Logining...");
//                // 这里是否应该考虑 必须先登入成功以后再进行订阅
//                // 1. 同步策略
//                // 2. 在收到订阅成功的消息后再进行订阅 --
////                wsClients.get(i).send(JSON.toJSONString(subscribeInfos.get(i)));
////                logger.info("Ws" + i + " Subscribing...");
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.info("Ws" + i + " Start Failed");
//            }
//        }
    }

    private void onOpenHandler(int clientNo) {
        logger.info("WS" + clientNo + " onOpen: Connection Established");
        wsClients.get(clientNo).send(JSON.toJSONString(loginInfos.get(clientNo)));
        logger.info("Ws" + clientNo + " Logining...");
        try {
            wsClients.get(clientNo).getSocket().setKeepAlive(true);
        } catch (SocketException e) {
            logger.info("Ws" + clientNo + "setKeepAlive Exception");
            e.printStackTrace();
        }
        wsClientsStatus.set(clientNo, WsStatus.OPEN);
    }

    // 前端控制台 手动添加 webSocket Client 并启动
    public void startNewWebSocketClient(String url, String userId, String password, String subAccount) {
        int clientNo = wsClients.size();
        LoginInfo loginInfo = new LoginInfo("login", new Info(userId, password));
        loginInfos.add(loginInfo);
        accountList.add(clientNo,subAccount);
        int instrumentListSize =wsInfos.getInstrumentIdList().size();
        Topic[] topics= new Topic[instrumentListSize];
        for (int i = 0; i < wsInfos.getInstrumentIdList().size(); i++) {
            topics[i] = new Topic(subAccount,wsInfos.getInstrumentIdList().get(i));
        }
        SubscribeInfo subscribeInfo = new SubscribeInfo("subscribe",topics);
        subscribeInfos.add(subscribeInfo);

        wsClientsStatus.add(clientNo, WsStatus.INIT);
        try {
            WebSocketClient newClient = new WebSocketClient(new URI(url)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    onOpenHandler(clientNo);
                }

                @Override
                public void onMessage(String s) {
                    boolean isReadable = onMessageService.messageDispatch(s, accountList.get(clientNo), clientNo);
                    if (!isReadable)
//                        logger.info("WebSocket_1 =====Unknown Message Received");
                        logger.info("WS" + clientNo + " onMessage: Unknown Message Received");

                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    logger.info("WS" + clientNo + " onClose: Connection Closed");
                    // 重新连接
                    wsClientsStatus.set(clientNo, WsStatus.CLOSE);
//                    wsClients.get(clientNo).connect();
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    logger.info("WS" + clientNo + " onError: Websocket Exception");
                    wsClientsStatus.set(clientNo, WsStatus.ERROR);
                }
            };
            newClient.connect();
            logger.info("WS" + clientNo + " :Connecting...");
            wsClients.add(newClient);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Exception Occur in WsClient Init");
        }
    }


    @Override
    public void close() {
        for (int i = 0; i < wsClients.size(); i++) {
            if (wsClients.get(i).getReadyState() != WebSocket.READYSTATE.OPEN) {
                logger.info("WebSocket Close: Skip Ws" + i);
                continue;
            }
            wsClients.get(i).close();
            logger.info("Ws" + i + " Closing...");
        }
    }

    @Override
    public boolean isClientNull() {
        for (WebSocketClient wsClient : wsClients) {
            if (wsClient == null)
                return true;
        }
        return false;
    }
//        return wsClient == null || wsClient2 == null || wsClient3 == null || wsClient4 == null;

    @Override
    public boolean isClientOpen() {
        for (WebSocketClient wsClient : wsClients) {
            if (!wsClient.getReadyState().equals(WebSocket.READYSTATE.OPEN))
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
        for (WebSocketClient wsClient : wsClients)
            if (!wsClient.isClosed())
                return false;
        return true;
    }
//        return wsClient.isClosed() && wsClient2.isClosed() && wsClient3.isClosed() && wsClient4.isClosed();

    @Override
    public List<WebSocketClient> getWsClients() {
        return wsClients;
    }

    public List<WsStatus> getWsClientsStatus() {
        return wsClientsStatus;
    }
}
