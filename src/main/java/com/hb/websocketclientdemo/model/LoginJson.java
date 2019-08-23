package com.hb.websocketclientdemo.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hb.websocketclientdemo.model.loginAndSubscribe.LoginInfo;

@JsonInclude(JsonInclude.Include.NON_NULL) //null 不转化
public class LoginJson {
    private String channel;

    @JSONField(name = "info")
    private LoginInfo loginInfo;

    public LoginJson(String channel, LoginInfo loginInfo) {
        this.channel = channel;
        this.loginInfo = loginInfo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }


}
