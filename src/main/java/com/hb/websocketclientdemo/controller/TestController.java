package com.hb.websocketclientdemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hb.websocketclientdemo.model.Topic;
import com.hb.websocketclientdemo.service.WebSocketService;
import org.java_websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private WebSocketService webSocketService;

    @RequestMapping("/")
    @ResponseBody
    public String hello() {
        return "hello,ll";
    }

    @RequestMapping("/connect")
    @ResponseBody
    public String connect() {
        if (webSocketService.getWsClient() == null ||
                !webSocketService.getWsClient().getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            webSocketService.connect();
            try {
                webSocketService.login("gt_w", "higgspass");
                Topic[] topics = new Topic[1];
                topics[0] = new Topic("83925101", "IF1908");
                webSocketService.subscribe(topics);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("remote server has been connected");
            return "connected";
        }
        return "Connecting";
    }

    @RequestMapping("/close")
    @ResponseBody
    public String close() {
        if (webSocketService.getWsClient() == null) {
            return "connection not established";
        }
        if (webSocketService.getWsClient().isClosed())
            return "closed ";
        else {
            webSocketService.close();
        }
        return "waiting to be closed";
    }

    @RequestMapping("/state")
    @ResponseBody
    public String state() {
        if (webSocketService.getWsClient() == null) {
            return "connection not established";
        }
        if (webSocketService.getWsClient().getReadyState().equals(WebSocket.READYSTATE.OPEN))
            return "connected";
        if (webSocketService.getWsClient().getReadyState().equals(WebSocket.READYSTATE.CLOSED))
            return "closed";
        if (webSocketService.getWsClient().getReadyState().equals(WebSocket.READYSTATE.CONNECTING))
            return "connecting";
        if (webSocketService.getWsClient().getReadyState().equals(WebSocket.READYSTATE.CLOSING))
            return "closing";
        if (webSocketService.getWsClient().getReadyState().equals(WebSocket.READYSTATE.NOT_YET_CONNECTED))
            return "not_yet_connected";
        return "error";

    }


}
