package com.hb.websocketclientdemo.service.model;

public enum OFFSET_FLAG {

    OPEN("0"),
    CLOSED("1"),
    CLOSED_TODAY("2");

    private String value;

    OFFSET_FLAG(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
