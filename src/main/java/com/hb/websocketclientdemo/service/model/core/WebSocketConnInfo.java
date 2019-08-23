package com.hb.websocketclientdemo.service.model.core;

import com.hb.websocketclientdemo.model.loginAndSubscribe.NewTopic;
import com.hb.websocketclientdemo.service.model.WsStatus;
import org.java_websocket.client.WebSocketClient;

import java.util.List;

public class WebSocketConnInfo {
    private String url;
    private String account;
    private String password;
    private int accountType;
    private NewTopic[] topics;
    private transient WsStatus status;
    private transient WebSocketClient client;
    private transient boolean isLogin;
    //instrumentId
    private transient List<String> subscribedInstrument;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public NewTopic[] getTopics() {
        return topics;
    }

    public void setTopics(NewTopic[] topics) {
        this.topics = topics;
    }

    public WsStatus getStatus() {
        return status;
    }

    public void setStatus(WsStatus status) {
        this.status = status;
    }

    public WebSocketClient getClient() {
        return client;
    }

    public void setClient(WebSocketClient client) {
        this.client = client;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public List<String> getSubscribedInstrument() {
        return subscribedInstrument;
    }

    public void setSubscribedInstrument(List<String> subscribedInstrument) {
        this.subscribedInstrument = subscribedInstrument;
    }
}
