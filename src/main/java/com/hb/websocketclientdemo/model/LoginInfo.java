package com.hb.websocketclientdemo.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) //null 不转化
public class LoginInfo {
    private String channel;

    private Info info;

    public LoginInfo(String channel, Info info) {
        this.channel = channel;
        this.info = info;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }


}
