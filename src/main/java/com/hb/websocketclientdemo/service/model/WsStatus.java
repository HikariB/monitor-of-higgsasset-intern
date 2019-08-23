package com.hb.websocketclientdemo.service.model;

public enum WsStatus {
    INIT(-1000),
    OPEN(-2000),
    LOGIN(-2001),
    SUBSCRIBED(-2002),
    CLOSE(-3000),
    ERROR(-4000);

    private int value;

    WsStatus(int status) {
        this.value = status;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int status) {
        this.value = status;
    }
}
