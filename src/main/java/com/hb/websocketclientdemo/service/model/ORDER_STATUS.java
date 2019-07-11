package com.hb.websocketclientdemo.service.model;

public enum ORDER_STATUS {
    DONE("0"),
    QUEUED("1"),
    CANCELD("2"),
    UNKNOWN("3");

//    Done = 0,
//    Queued = 1,
//    Canceled = 2,
//    Unknown = 3;

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    ORDER_STATUS(String value) {
        this.value = value;
    }
}
