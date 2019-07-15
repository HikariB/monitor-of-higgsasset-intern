package com.hb.websocketclientdemo.service.model;

import java.util.LinkedList;
import java.util.List;


public class LoginResult {
    private String result;
    private List<String> account;

    public List<String> getAccount() {
        return account;
    }

    public void setAccount(List<String> account) {
        this.account = account;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LoginResult() {
        this.result = " ";
        this.account = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "result='" + result + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
