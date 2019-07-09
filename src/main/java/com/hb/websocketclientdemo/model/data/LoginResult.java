package com.hb.websocketclientdemo.model.data;

import org.springframework.stereotype.Component;

@Component
public class LoginResult {
    private String result;
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
