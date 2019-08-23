package com.hb.websocketclientdemo.service;

import com.alibaba.fastjson.JSONObject;
import com.hb.websocketclientdemo.service.model.core.WebSocketConnInfo;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

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
        ClassPathResource resource = new ClassPathResource("websocket.json");
        try {
            String jsonFile = IOUtils.toString(resource.getInputStream(), Charset.forName("UTF-8"));
            webSocketConnInfos = JSONObject.parseArray(jsonFile, WebSocketConnInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Read JsonFile Failed");
        }
        assert webSocketConnInfos != null;
        logger.info("From Json ConfigFile To Object: " + webSocketConnInfos.size());
        return webSocketConnInfos;
    }

}
