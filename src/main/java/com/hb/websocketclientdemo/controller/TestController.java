package com.hb.websocketclientdemo.controller;

import com.hb.websocketclientdemo.model.LoginInfo;
import com.hb.websocketclientdemo.model.SubscribeInfo;
import com.hb.websocketclientdemo.model.data.jsonData.InstrumentInfoDO;
import com.hb.websocketclientdemo.service.WebSocketService;
import com.hb.websocketclientdemo.service.model.LoginResult;
import com.hb.websocketclientdemo.service.model.MonitorData;
import com.hb.websocketclientdemo.service.model.SubResult;
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

    @Autowired
    private LoginResult loginResult;

    @Autowired
    private SubResult subResult;

    @Autowired
    private InstrumentInfoDO instrumentInfo;

    @Autowired
    private MonitorData monitorData;

    @Autowired
    private LoginInfo loginInfo;

    @Autowired
    private SubscribeInfo subscribeInfo;


    @RequestMapping("/")
    @ResponseBody
    public String hello() {
        return "hello,ll";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/connect")
    @ResponseBody
    public String connect() {
        if (webSocketService.getWsClient() == null ||
                !webSocketService.getWsClient().getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            webSocketService.connect();
            webSocketService.login();
            webSocketService.subscribe();
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

    @RequestMapping("/login-result")
    @ResponseBody
    public LoginResult getLoginResult() {
        return loginResult;
    }

    @RequestMapping("/sub-result")
    @ResponseBody
    public SubResult getSubResult() {
        return subResult;
    }

    @RequestMapping("/instrument-info")
    @ResponseBody
    public InstrumentInfoDO getInstrumentInfo() {
        return instrumentInfo;
    }

    @RequestMapping("monitor-data")
    @ResponseBody
    public MonitorData getMonitorData() {
        return monitorData;
    }

    @RequestMapping("/config-login-bean")
    @ResponseBody
    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    @RequestMapping("/config-subscribe-bean")
    @ResponseBody
    public SubscribeInfo getSubscribeInfo() {
        return subscribeInfo;
    }

    @RequestMapping("/monitor")
    public String monitor(){
        return "monitor";
    }

}
