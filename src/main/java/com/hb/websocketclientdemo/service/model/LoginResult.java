package com.hb.websocketclientdemo.service.model;

public class LoginResult {
    private String result;
    private String account;

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "result='" + result + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
