package com.hb.websocketclientdemo.service.model;

public enum DIRECTION {
    SELL("1"), BUY("0");
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    DIRECTION(String value) {
        this.value = value;
    }
}
