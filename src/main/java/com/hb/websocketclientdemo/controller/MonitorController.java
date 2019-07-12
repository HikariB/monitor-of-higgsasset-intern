package com.hb.websocketclientdemo.controller;

import com.hb.websocketclientdemo.service.model.InstrumentData;
import com.hb.websocketclientdemo.service.model.MonitorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class MonitorController {

    @Autowired
    private MonitorData monitorData;

    @RequestMapping("/instruments")
    public List<InstrumentData> getInstrumentInfo() {
        List<InstrumentData> res = new LinkedList<>();
        Map<String, InstrumentData> instruments = monitorData.getInstruments();
        instruments.forEach((k, v) -> {
            res.add(v);
        });
        return res;
    }





}
