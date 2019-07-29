package com.hb.websocketclientdemo.controller;

import com.hb.websocketclientdemo.controller.viewObj.AccountSummary;
import com.hb.websocketclientdemo.model.LoginInfo;
import com.hb.websocketclientdemo.model.SubscribeInfo;
import com.hb.websocketclientdemo.service.WSServerInfoConfig;
import com.hb.websocketclientdemo.service.impl.WebSocketService;
import com.hb.websocketclientdemo.service.model.*;
import com.hb.websocketclientdemo.service.model.Core.MultiAccountMonitorData;
import org.java_websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);
    @Autowired
    private MultiAccountMonitorData multiAccountData;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private WSServerInfoConfig wsServerInfos;

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
            double volumeRatio = (marketVolumeSum == 0) ? 0 : (1.0 * tradeVolumeSum / (marketVolumeSum));
//            double positionCost = monitorData.getInstruments().values().stream().mapToDouble(InstrumentData::getPositionCost).sum();
            double feeSum = monitorData.getInstruments().values().stream().mapToDouble(InstrumentData::getFee).sum();
            double orderFeeSum = monitorData.getInstruments().values().stream().mapToDouble(InstrumentData::getOrderFee).sum();
            double profitSum = monitorData.getInstruments().values().stream().mapToDouble(InstrumentData::getProfit).sum();
            double profitNonSum = profitSum + orderFeeSum + feeSum;
            boolean profitWarn = (profitSum < AccountSummary.TOTAL_PROFIT_LIMIT);

            summary.setTradeVolumeSum(tradeVolumeSum);
            summary.setVolumeRatio(volumeRatio);
//            summary.setPositionCost(positionCost);
            summary.setFeeSum(feeSum);
            summary.setOrderFeeSum(orderFeeSum);
            summary.setProfitSum(profitSum);
            summary.setProfitNonNetSum(profitNonSum);
            summary.setTotalProfitWarn(profitWarn);

            res.add(summary);
        });
        return res;
    }

    @RequestMapping("/login-result")
    public LoginResult getLoginResult() {
        return multiAccountData.getLoginResult();
    }

    @RequestMapping("/sub-result")
    public SubResult getSubResult() {
        return multiAccountData.getSubResult();
    }

    @RequestMapping("monitor-data/{accountId}")
    public MonitorData getMonitorData(@PathVariable String accountId) {
        return multiAccountData.getAccountsInfo().get(accountId);
    }

    /**
     * connect
     * 先断开，再重连是有问题
     * @return
     */
    @RequestMapping("/connect")
    public String connect() {
        if (webSocketService.isClientNull() ||
                !webSocketService.isClientOpen()) {
            webSocketService.getWsClients().clear();
            webSocketService.webSocketStart();
        } else {
            logger.info("remote server has been connected");
            return "connected";
        }
        return "Connecting";
    }

    @RequestMapping("/close-all-client")
    public String close() {
        if (webSocketService.isClientNull()) {
            return "connection not established";
        }
        if (webSocketService.isClosed())
            return "closed ";
        else {
            webSocketService.close();
        }
        return "waiting to be closed";
    }

    @RequestMapping("/client-state/{clientId}")
    public String state(@PathVariable int clientId) {
        if (webSocketService.getWsClients().get(clientId) == null) {
            return "connection not established";
        }
        if (webSocketService.getWsClients().get(clientId).getReadyState().equals(WebSocket.READYSTATE.OPEN))
            return "connected";
        if (webSocketService.getWsClients().get(clientId).getReadyState().equals(WebSocket.READYSTATE.CLOSED))
            return "closed";
        if (webSocketService.getWsClients().get(clientId).getReadyState().equals(WebSocket.READYSTATE.CONNECTING))
            return "connecting";
        if (webSocketService.getWsClients().get(clientId).getReadyState().equals(WebSocket.READYSTATE.CLOSING))
            return "closing";
        if (webSocketService.getWsClients().get(clientId).getReadyState().equals(WebSocket.READYSTATE.NOT_YET_CONNECTED))
            return "not_yet_connected";
        return "error";

    }

    @RequestMapping("/config-login-bean")
    public List<LoginInfo> getLoginInfo() {
        return wsServerInfos.getLoginInfos();
    }

    @RequestMapping("/config-subscribe-bean")
    public List<SubscribeInfo> getSubscribeInfo() {
        return wsServerInfos.getSubscribeInfos();
    }

    @RequestMapping("/all-data")
    public MultiAccountMonitorData getAllData() {
        return multiAccountData;
    }

}
