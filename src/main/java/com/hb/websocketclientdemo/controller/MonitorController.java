package com.hb.websocketclientdemo.controller;

import com.hb.websocketclientdemo.model.AccountSummary;
import com.hb.websocketclientdemo.service.model.InstrumentData;
import com.hb.websocketclientdemo.service.model.MonitorData;
import com.hb.websocketclientdemo.service.model.MultiAccountMonitorData;
import com.hb.websocketclientdemo.service.model.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class MonitorController {


    @Autowired
    private MultiAccountMonitorData multiAccountData;

    @RequestMapping("/instruments/{accountID}")
    public List<InstrumentData> getInstrumentInfo(@PathVariable String accountID) {
        MonitorData monitorData = multiAccountData.getAccountsInfo().get(accountID);

        //      试一下Java8 Stream功能
        return monitorData.getInstruments().values().stream().collect(Collectors.toList());

//        List<InstrumentData> res = new LinkedList<>();
//        Map<String, InstrumentData> instruments = monitorData.getInstruments();
//        instruments.forEach((k, v) -> res.add(v));

//        return res;
    }

    @RequestMapping("/orders/{accountID}")
    public List<OrderData> getOrderData(@PathVariable String accountID) {
        MonitorData monitorData = multiAccountData.getAccountsInfo().get(accountID);
        List<OrderData> res = new LinkedList<>();
        Map<String, Map<Integer, OrderData>> orderMap = monitorData.getOrders();
        orderMap.forEach((k, orders) -> orders.forEach((orderId, orderData) -> {
            res.add(orderData);
        }));
        return res;
    }

    @RequestMapping("/summary")
    public List<AccountSummary> getSummary() {
        List<AccountSummary> res = new LinkedList<>();
        multiAccountData.getAccountsInfo().forEach((k, monitorData) -> {
            AccountSummary summary = new AccountSummary();
            summary.setAccountId(k);

            int tradeVolumeSum = monitorData.getInstruments().values().stream().mapToInt(InstrumentData::getTradeVolume).sum();
            int marketVolumeSum = monitorData.getInstruments().values().stream().mapToInt(InstrumentData::getVolume).sum();
            marketVolumeSum = (marketVolumeSum == 0) ? 1 : marketVolumeSum;
            double volumeRatio = 1.0 * tradeVolumeSum / (marketVolumeSum);
            double positionCost = monitorData.getInstruments().values().stream().mapToDouble(InstrumentData::getPositionCost).sum();
            double feeSum = monitorData.getInstruments().values().stream().mapToDouble(InstrumentData::getFee).sum();
            double orderFeeSum = monitorData.getInstruments().values().stream().mapToDouble(InstrumentData::getOrderFee).sum();
            double profitSum = monitorData.getInstruments().values().stream().mapToDouble(InstrumentData::getProfit).sum();


            summary.setTradeVolumeSum(tradeVolumeSum);
            summary.setVolumeRatio(volumeRatio);
            summary.setPositionCost(positionCost);
            summary.setFeeSum(feeSum);
            summary.setOrderFeeSum(orderFeeSum);
            summary.setProfitSum(profitSum);

            res.add(summary);


        });
        return res;
    }


}
