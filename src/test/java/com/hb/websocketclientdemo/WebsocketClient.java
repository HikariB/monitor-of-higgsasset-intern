package com.hb.websocketclientdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.websocketclientdemo.model.Info;
import com.hb.websocketclientdemo.model.LoginInfo;
import com.hb.websocketclientdemo.model.SubscribeInfo;
import com.hb.websocketclientdemo.model.Topic;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebsocketClient {
    public static WebSocketClient client;



    public static void main(String[] args) throws JsonProcessingException {

        try {
//            ws://10.12.226.66:8085/socket
//            ws://114.55.210.206:9999/


            client = new WebSocketClient(new URI("ws://114.55.210.206:9999/"),new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("打开链接");
                }

                @Override
                public void onMessage(String s) {
                    System.out.println("收到消息:"+s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("链接已关闭");
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    System.out.println("发生错误已关闭");
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        client.connect();
        System.out.println(client.getDraft());
        while(!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
            System.out.println("还没有打开");
        }
        System.out.println("打开了");

        ObjectMapper mapper = new ObjectMapper();
        Info info = new Info("gt_w","higgspass");
        LoginInfo loginInfo = new LoginInfo("login",info);

        client.send(mapper.writeValueAsString(loginInfo));


        SubscribeInfo subscribeInfo = new SubscribeInfo(null,new Topic[1]);
        subscribeInfo.setChannel("subscribe");
        subscribeInfo.getTopics()[0] = new Topic("83925101","IF1908");
//        subscribeInfo.getTopics()[1] = new Topic("83925087","IF1905");
//        subscribeInfo.getTopics()[2] = new Topic("83925087","IF1906");
        client.send(mapper.writeValueAsString(subscribeInfo));


//        try {
//            send("hello world".getBytes("utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        for (int i=0;i<=5;i++) {
//            client.send("hello");
////        client.close();
//        }



    }


    public static void send(byte[] bytes){
        client.send(bytes);
    }

}

