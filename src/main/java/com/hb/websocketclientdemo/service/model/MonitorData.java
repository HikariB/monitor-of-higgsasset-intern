package com.hb.websocketclientdemo.service.model;

import java.util.HashMap;
import java.util.Map;


public class MonitorData {
    //string: instrumentId
    private Map<String, InstrumentData> instruments;
    private Map<String,Map<Integer, OrderData>> orders;
    private int wsClientNum;


    public MonitorData(int wsClientNo){
        instruments = new HashMap<>();
        orders = new HashMap<>();
        wsClientNum = wsClientNo;
//        accountSummary = new AccountSummary();
    }

    public MonitorData(){
        instruments = new HashMap<>();
        orders = new HashMap<>();
//        accountSummary = new AccountSummary();
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

    public int getWsClientNum() {
        return wsClientNum;
    }

    public void setWsClientNum(int wsClientNum) {
        this.wsClientNum = wsClientNum;
    }
}
