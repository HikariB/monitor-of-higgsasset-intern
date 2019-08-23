package com.hb.websocketclientdemo.service.model.core;

import com.hb.websocketclientdemo.service.model.AccountType;
import com.hb.websocketclientdemo.service.model.base.InstrumentData;
import com.hb.websocketclientdemo.service.model.base.OrderData;

import java.util.HashMap;
import java.util.Map;


public class AccountData {
    //string: instrumentId
    private Map<String, InstrumentData> instruments;
    private Map<String, Map<Integer, OrderData>> orders;
    private WebSocketConnInfo connInfo;
    private transient AccountType accountType;

    public AccountData(WebSocketConnInfo connInfo,AccountType type) {
        instruments = new HashMap<>();
        orders = new HashMap<>();
        this.connInfo = connInfo;
        this.accountType = type;
    }


    public AccountData() {
        instruments = new HashMap<>();
        orders = new HashMap<>();
    }

    public Map<String, InstrumentData> getInstruments() {
        return instruments;
    }

    public void setInstruments(Map<String, InstrumentData> instruments) {
        this.instruments = instruments;
    }

    public Map<String, Map<Integer, OrderData>> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, Map<Integer, OrderData>> orders) {
        this.orders = orders;
    }

    public WebSocketConnInfo getConnInfo() {
        return connInfo;
    }

    public void setConnInfo(WebSocketConnInfo connInfo) {
        this.connInfo = connInfo;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
