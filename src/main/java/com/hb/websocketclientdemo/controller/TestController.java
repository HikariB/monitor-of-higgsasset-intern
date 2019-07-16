package com.hb.websocketclientdemo.controller;

import com.hb.websocketclientdemo.model.LoginInfo;
import com.hb.websocketclientdemo.model.SubscribeInfo;
import com.hb.websocketclientdemo.service.WebSocketService;
import com.hb.websocketclientdemo.service.model.LoginResult;
import com.hb.websocketclientdemo.service.model.MonitorData;
import com.hb.websocketclientdemo.service.model.MultiAccountMonitorData;
import com.hb.websocketclientdemo.service.model.SubResult;
import org.java_websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private MultiAccountMonitorData multiAccountData;

    @Resource(name = "LoginInfoWS2")
    private LoginInfo loginInfo;

    @Resource(name = "SubscribeInfoWS2")
    private SubscribeInfo subscribeInfo;


    @RequestMapping("/")
    @ResponseBody
    public String hello() {
        return "This is For Test";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/connect")
    @ResponseBody
    public String connect() {
        if ( webSocketService.isClientNull()||
                !webSocketService.isClientOpen()) {
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
        if (webSocketService.isClientNull()) {
            return "connection not established";
        }
        if (webSocketService.isClosed())
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
        return multiAccountData.getLoginResult();
    }

    @RequestMapping("/sub-result")
    @ResponseBody
    public SubResult getSubResult() {
        return multiAccountData.getSubResult();
    }

    @RequestMapping("monitor-data")
    @ResponseBody
    public MonitorData getMonitorData() {
        return multiAccountData.getAccountsInfo().get("83925101");
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

    @RequestMapping("/monitor-detail")
    public String monitor(){
        return "monitor-detail";
    }

    @RequestMapping("/login")
    public String login() {
        return "login-sign";
    }

    @RequestMapping("/login-error")
    public String loginError() {
        return "login-error";
    }

    @RequestMapping("/monitor-summary")
    public String summaryPage() {
        return "monitor-summary";
    }


    @RequestMapping("/all-data")
    @ResponseBody
    public MultiAccountMonitorData getAllData(){
        return multiAccountData;
    }
}
