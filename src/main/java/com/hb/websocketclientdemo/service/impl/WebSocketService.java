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
            this.connect(connInfo, countDownLatch);
        }
        try {
            countDownLatch.await(CONNECTION_TIME_OUT, TimeUnit.SECONDS);
            logger.info("WebsocketClient 尚未连接数：" + countDownLatch.getCount());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Waiting For All WebSocketClient Connection Established , CountDownLatch Error");
        }
    }

    public void connect(WebSocketConnInfo connInfo) {
        countDownLatch = new CountDownLatch(1);
        this.connect(connInfo,countDownLatch);
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



    public void connect(WebSocketConnInfo connInfo, CountDownLatch countDownLatch) {
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
