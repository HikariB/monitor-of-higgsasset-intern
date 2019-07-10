package com.hb.websocketclientdemo.model.data.dataVO;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MonitorData {
    //string: instrumentId
    private Map<String,InstrumentData> instruments;
    private Map<String,Map<Integer,OrderData>> orders;

    public MonitorData(){
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
}
