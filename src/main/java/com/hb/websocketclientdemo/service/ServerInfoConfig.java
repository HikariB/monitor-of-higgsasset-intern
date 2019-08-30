package com.hb.websocketclientdemo.service;

import com.alibaba.fastjson.JSONObject;
import com.hb.websocketclientdemo.controller.viewObj.AccountSummary;
import com.hb.websocketclientdemo.service.impl.OnMessageService;
import com.hb.websocketclientdemo.service.model.ConstConfig;
import com.hb.websocketclientdemo.service.model.base.InstrumentData;
import com.hb.websocketclientdemo.service.model.core.WebSocketConnInfo;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 配置存放在websocket.json
 */
@Configuration
public class ServerInfoConfig {

    private static final Logger logger = LoggerFactory.getLogger(ServerInfoConfig.class);


    @Bean
    public List<WebSocketConnInfo> getConnInfos() {
        List<WebSocketConnInfo> webSocketConnInfos = null;
        // newConfig.json
        // src/main/resources/newConfig.Json
//        ClassPathResource resource = new ClassPathResource("newConfig.Json");
        File file = new File("src/main/resources/newConfig.json");
        try {
//            String jsonFile = IOUtils.toString(resource.getInputStream(), Charset.forName("UTF-8"));
            String jsonFile = FileUtils.readFileToString(file,Charset.forName("UTF-8"));
            webSocketConnInfos = JSONObject.parseArray(jsonFile, WebSocketConnInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Read JsonFile Failed: newConfig.json");
        }
        assert webSocketConnInfos != null;
        logger.info("From Json ConfigFile To Object: " + webSocketConnInfos.size());
        return webSocketConnInfos;
    }

    @Bean
    public ConstConfig getConstConfig() {
//        ClassPathResource resource = new ClassPathResource("const.json");
        File file = new File("src/main/resources/const.json");
        ConstConfig ccfg = null;
        try {
//            String jsonFile = IOUtils.toString(resource.getInputStream(), Charset.forName("UTF-8"));

            String jsonFile = FileUtils.readFileToString(file,Charset.forName("UTF-8"));

            ccfg = (ConstConfig) JSONObject.parseObject(jsonFile, ConstConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Read JsonFile Failed: const.json");
        }
        assert ccfg != null;
        AccountSummary.setTotalProfitLimit(ccfg.getAccountTotalProfitLimit());
        InstrumentData.setProfitLimit(ccfg.getInstrumentProfitLimit());
        InstrumentData.setCancelWarnRatio(ccfg.getOrderCancelWarnRatio());
        InstrumentData.setOrderCancelLimit(ccfg.getOrderCancelLimit());
        InstrumentData.setNetPositionLimit(ccfg.getNetPositionLimit());
        OnMessageService.setDelayMax(ccfg.getmDelaySecLimit());
        return ccfg;
    }

}

