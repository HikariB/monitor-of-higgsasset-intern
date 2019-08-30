package com.hb.websocketclientdemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hb.websocketclientdemo.controller.viewObj.AccountSummary;
import com.hb.websocketclientdemo.model.loginAndSubscribe.NewTopic;
import com.hb.websocketclientdemo.service.impl.OnMessageService;
import com.hb.websocketclientdemo.service.impl.WebSocketService;
import com.hb.websocketclientdemo.service.model.AccountType;
import com.hb.websocketclientdemo.service.model.ConstConfig;
import com.hb.websocketclientdemo.service.model.WsStatus;
import com.hb.websocketclientdemo.service.model.base.InstrumentData;
import com.hb.websocketclientdemo.service.model.base.OrderData;
import com.hb.websocketclientdemo.service.model.core.AccountData;
import com.hb.websocketclientdemo.service.model.core.WebSocketConnInfo;
import org.apache.commons.io.FileUtils;
import org.java_websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;

@RestController
public class MonitorController {
    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);

    @Autowired
    private Map<String, AccountData> allData;

    @Autowired
    private List<WebSocketConnInfo> connInfos;

    @Autowired
    private Map<String, WebSocketConnInfo> connInfoMap;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private ConstConfig ccfg;


    @RequestMapping("/clients/lr")
    public Map<String, Boolean> getLoginResult() {
        Map<String, Boolean> loginResult = new HashMap<>();
        for (WebSocketConnInfo connInfo : connInfos) {
            loginResult.put(connInfo.getAccount(), connInfo.isLogin());
        }
        return loginResult;
    }

    @RequestMapping("/clients/sr")
    public Map<String, List<String>> getSubResult() {
        Map<String, List<String>> subResult = new HashMap<>();
        for (WebSocketConnInfo connInfo : connInfos) {
            subResult.put(connInfo.getAccount(), connInfo.getSubscribedInstrument());
        }
        return subResult;
    }

    /**
     * connect
     * 先断开，再重连是有问题
     */
    @RequestMapping("/clients/reconnect")
    public String connect() {
        // 重连
        new Thread(new Runnable() {
            @Override
            public void run() {
                webSocketService.shutdown();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (WebSocketConnInfo connInfo : connInfos) {
                    connInfo.setStatus(WsStatus.INIT);
                    connInfo.setClient(null);
                    connInfo.setLogin(false);
                    connInfo.setSubscribedInstrument(null);
                }
                allData.clear();
                webSocketService.connect(connInfos);
            }
        }).start();
        logger.info("ReConnect Request Received...");
        return "Wait a moment...To be restart";
    }

    @RequestMapping("/clients/shutdown")
    public String close() {
        for (WebSocketConnInfo connInfo : connInfos) {
            if (connInfo.getClient() == null || !connInfo.getClient().getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                return "Not Initialized or Already Closed";
            }
        }
        webSocketService.shutdown();
        return "Wait a moment...To be shutdown";
    }


    /**
     * Query Data
     */

    @RequestMapping("/data/{accountId}")
    public AccountData getMonitorData(@PathVariable String accountId) {
        return allData.get(accountId);
    }

    @RequestMapping("/data/all")
    public String queryAll() {
        return JSON.toJSONString(allData);
    }

    @RequestMapping("/data/sif/summary")
    public List<AccountSummary> getSifSummary() {
        List<AccountSummary> res = new ArrayList<>();
        allData.forEach((k, accountData) -> {
            if (accountData.getAccountType().equals(AccountType.STOCK_INDEX_FT)) {
                res.add(getAccountSummaryFromAccountData(accountData));
            }
        });
        return res;
    }


    @RequestMapping("/data/cf/summary")
    public List<AccountSummary> getCfSummary() {
        List<AccountSummary> res = new ArrayList<>();
        allData.forEach((k, accountData) -> {
            if (accountData.getAccountType().equals(AccountType.COMMODITY_FT)) {
                res.add(getAccountSummaryFromAccountData(accountData));
            }
        });
        return res;
    }

    @RequestMapping("/data/bf/summary")
    public List<AccountSummary> getBfSummary() {
        List<AccountSummary> res = new ArrayList<>();
        allData.forEach((k, accountData) -> {
            if (accountData.getAccountType().equals(AccountType.BOND_FT)) {
                res.add(getAccountSummaryFromAccountData(accountData));
            }
        });
        return res;
    }

    @RequestMapping("/data/opt/summary")
    public List<AccountSummary> getOptSummary() {
        List<AccountSummary> res = new ArrayList<>();
        allData.forEach((k, accountData) -> {
            if (accountData.getAccountType().equals(AccountType.OPT)) {
                res.add(getAccountSummaryFromAccountData(accountData));
            }
        });
        return res;
    }


    // monitor-detail 请求的信息
    @RequestMapping("/instruments/{accountID}")
    public List<InstrumentData> getInstrumentInfo(@PathVariable String accountID) {
        AccountData accountData = allData.get(accountID);
        if (accountData == null)
            return null;
        return new ArrayList<>(accountData.getInstruments().values());
    }

    @RequestMapping("/orders/{accountID}")
    public List<OrderData> getOrderData(@PathVariable String accountID) {
        AccountData accountData = allData.get(accountID);
        if (accountData == null)
            return null;
        List<OrderData> res = new ArrayList<>();
        Map<String, Map<Integer, OrderData>> orderMap = accountData.getOrders();
        orderMap.forEach((k, orders) ->
                orders.forEach((orderId, orderData) -> {
                    res.add(orderData);
                }));
        return res;
    }


    //Control Form INDEX
    @RequestMapping(value = "/set/para", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    public String setPara(
            @RequestParam(name = "totalProfitLimit") Double totalProfitLimit,
            @RequestParam(name = "profitLimit") Double instrumentProfitLimit,
            @RequestParam(name = "cancelWarnRatio") Double cancelWarnRatio,
            @RequestParam(name = "orderCancelLimit") Integer orderCancelLimit,
            @RequestParam(name = "netPositionLimit") Integer netPositionLimit,
            @RequestParam(name = "mDDelaySecLimit") Integer maxDelay
    ) {
        AccountSummary.setTotalProfitLimit(totalProfitLimit);
        InstrumentData.setProfitLimit(instrumentProfitLimit);
        InstrumentData.setCancelWarnRatio(cancelWarnRatio);
        InstrumentData.setOrderCancelLimit(orderCancelLimit);
        InstrumentData.setNetPositionLimit(netPositionLimit);
        OnMessageService.setDelayMax(maxDelay);
        ccfg.setAccountTotalProfitLimit(totalProfitLimit);
        ccfg.setInstrumentProfitLimit(instrumentProfitLimit);
        ccfg.setOrderCancelWarnRatio(cancelWarnRatio);
        ccfg.setOrderCancelLimit(orderCancelLimit);
        ccfg.setNetPositionLimit(netPositionLimit);
        ccfg.setmDelaySecLimit(maxDelay);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File("src/main/resources/const.Json");
                    logger.info("Update JsonConfigFile to: " + JSON.toJSONString(ccfg));
                    FileUtils.writeStringToFile(file, JSON.toJSONString(ccfg), Charset.forName("UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return "Saved to const.json and update parameter";
    }


    @RequestMapping(value = "account/config/edit", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    public String editAccountConfig(
            @RequestParam(name = "cmdOpt") String cmdOpt,
            @RequestParam(name = "account") String account,
            @RequestParam(name = "editContent") String editContent
    ) {
        if (account.equals("*") && cmdOpt.equals("selCmd")) {
            return JSON.toJSONString(connInfos);
        }
        String res = "[Error]: No Such Command";
        if (cmdOpt.equals("delCmd") || cmdOpt.equals("selCmd")) {
            if (connInfoMap.containsKey(account)) {
                if (cmdOpt.equals("selCmd")) {
                    return JSON.toJSONString(connInfoMap.get(account));
                }
                //  del
                res = "[Before Deleted]: " + JSON.toJSONString(connInfoMap.get(account));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        connInfoMap.get(account).getClient().close();
                        connInfos.remove(connInfoMap.get(account));
                        connInfoMap.remove(account);
                        allData.remove(account);
                        // write to file
                        writeConnInfosToFile();
                    }
                }).start();
                return res;
            } else {
                return "[Error]: Account Not Exist";
            }
        }
        // update
        if (cmdOpt.equals("updCmd")) {
            try {
                WebSocketConnInfo newConn = JSONObject.parseObject(editContent, WebSocketConnInfo.class);
                String newAccount = newConn.getAccount();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (connInfoMap.containsKey(newAccount)) {
                            // delete old conn
                            connInfoMap.get(account).getClient().close();
                            connInfos.remove(connInfoMap.get(account));
                            connInfoMap.remove(account);
                            allData.remove(account);
                        }
                        // restart
                        connInfos.add(newConn);
                        connInfoMap.put(newAccount, newConn);
                        webSocketService.connect(newConn);
                        writeConnInfosToFile();
                    }
                }).start();
                return "Update Success, Try [Select *] to check";
            } catch (Exception e) {
                e.printStackTrace();
                return "[JsonString Error]";
            }
        }
        return res;
    }


    @RequestMapping(value = "account/subscribe/edit", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    public String editSubscribe(
            @RequestParam(name = "cmdOpt") String cmdOpt,
            @RequestParam(name = "account") String account,
            @RequestParam(name = "editContent") String editContent
    ) {
        if (account.equals("*") && cmdOpt.equals("selCmd")) {
            return JSON.toJSONString(connInfos);
        }
        boolean isAccountValid = connInfoMap.containsKey(account);
        if (!isAccountValid) {
            return "[Error]: No Such Account";
        }
        boolean isUpdate = false;
        String res = "[Error]: No Such Command";
        NewTopic[] topics = new NewTopic[0];
        if (cmdOpt.equals("selCmd")) {
            return JSON.toJSONString(connInfoMap.get(account).getTopics());
        } else if (cmdOpt.equals("updCmd")) {
            try {
                List<NewTopic> topicList = JSONObject.parseArray(editContent, NewTopic.class);
                topics = new NewTopic[topicList.size()];
                topicList.toArray(topics);
                res = "[Override the topics]";
                isUpdate = true;
            } catch (Exception e) {
                e.printStackTrace();
                res = "[JsonString Error]";
            }
        } else if (cmdOpt.equals("delCmd")) {
            isUpdate = true;
            res = "[Before Deleted]: " + JSON.toJSONString(connInfoMap.get(account).getTopics());
        }

        if (isUpdate) {
            //change to the file
            NewTopic[] finalTopics = topics;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //restart the client
                    WebSocketConnInfo connInfo = connInfoMap.get(account);
                    connInfo.getClient().close();
                    connInfo.setTopics(finalTopics);
                    connInfo.setStatus(WsStatus.INIT);
                    connInfo.setClient(null);
                    connInfo.setLogin(false);
                    connInfo.setSubscribedInstrument(null);
                    allData.remove(account);
                    webSocketService.connect(connInfo);
                    writeConnInfosToFile();
                }
            }).start();
        }
        return res;
    }

    private boolean writeConnInfosToFile() {
        try {
            File file = new File("src/main/resources/newConfig.Json");
            logger.info("Update JsonConfigFile to: " + JSON.toJSONString(connInfos));
            FileUtils.writeStringToFile(file, JSON.toJSONString(connInfos), Charset.forName("UTF-8"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private AccountSummary getAccountSummaryFromAccountData(AccountData accountData) {

        AccountSummary summary = new AccountSummary();
        summary.setAccountId(accountData.getConnInfo().getAccount());
        int tradeVolumeSum = accountData.getInstruments().values().stream().mapToInt(InstrumentData::getTradeVolume).sum();
        int marketVolumeSum = accountData.getInstruments().values().stream().mapToInt(InstrumentData::getVolume).sum();
        double volumeRatio = (marketVolumeSum == 0) ? 0 : (1.0 * tradeVolumeSum / (marketVolumeSum));
//            double positionCost = accountData.getInstruments().values().stream().mapToDouble(InstrumentData::getPositionCost).sum();
        double feeSum = accountData.getInstruments().values().stream().mapToDouble(InstrumentData::getFee).sum();
        double orderFeeSum = accountData.getInstruments().values().stream().mapToDouble(InstrumentData::getOrderFee).sum();
        double profitSum = accountData.getInstruments().values().stream().mapToDouble(InstrumentData::getProfit).sum();
        double profitNonSum = profitSum + orderFeeSum + feeSum;
        boolean profitWarn = (profitSum < AccountSummary.TOTAL_PROFIT_LIMIT);
        // 全部合同没有延迟，则该账号无延迟，否则有延迟
        boolean isMarketDataValid = accountData.getInstruments().values().stream().allMatch(InstrumentData::isMarketDataValid);
        // 合同中的最大延迟时间
        Optional<Long> maxDelayOP = accountData.getInstruments().values().stream().map(InstrumentData::getMDDelaySec).max(Long::compareTo);
        WsStatus clientStatus = accountData.getConnInfo().getStatus();
        long maxDelay = (clientStatus == WsStatus.SUBSCRIBED && maxDelayOP.isPresent()) ? maxDelayOP.get() : clientStatus.getValue();

        summary.setTradeVolumeSum(tradeVolumeSum);
        summary.setVolumeRatio(volumeRatio);
//            summary.setPositionCost(positionCost);
        summary.setFeeSum(feeSum);
        summary.setOrderFeeSum(orderFeeSum);
        summary.setProfitSum(profitSum);
        summary.setProfitNonNetSum(profitNonSum);
        summary.setTotalProfitWarn(profitWarn);
        summary.setMarketDataValid(isMarketDataValid);
        summary.setMaxMDDelaySec(maxDelay);
        return summary;
    }

}
