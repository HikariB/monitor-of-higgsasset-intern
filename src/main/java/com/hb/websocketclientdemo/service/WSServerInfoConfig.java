package com.hb.websocketclientdemo.service;

import com.hb.websocketclientdemo.model.Info;
import com.hb.websocketclientdemo.model.LoginInfo;
import com.hb.websocketclientdemo.model.SubscribeInfo;
import com.hb.websocketclientdemo.model.Topic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * ws服务器配置类
 * 每个服务器需要新增url+loginAccount+SubAccount
 * 配置存放在websocket.properties
 *
 */
@Configuration
@PropertySource("classpath:websocket.properties")
@ConfigurationProperties(prefix = "websocket")
public class WSServerInfoConfig {

    private List<String> urlList = new ArrayList<>();

    private List<String> loginAccountList = new ArrayList<>();

    private List<String> subAccountList = new ArrayList<>();

    private List<String> instrumentIdList = new ArrayList<>();

    private String password;

    /**
     * 根据url个数即服务器个数N
     * 产生N个loginInfo，便于后续websocket.send产生Json String
     * @return
     */
    @Bean
    public List<LoginInfo> getLoginInfos() {
        List<LoginInfo> loginInfos = new ArrayList<>();
        for (int i = 0; i < urlList.size(); i++) {
            LoginInfo loginInfo = new LoginInfo("login", new Info(loginAccountList.get(i), password));
            loginInfos.add(loginInfo);
        }
        return loginInfos;
    }

    /**
     * 产生N个subscribeInfos，便于后续websocket.send产生Json String
     * @return
     */
    @Bean
    public List<SubscribeInfo> getSubscribeInfos(){
        List<SubscribeInfo> subscribeInfos = new ArrayList<>();
        for (int i = 0; i <subAccountList.size() ; i++) {
            Topic[] topics = new Topic[instrumentIdList.size()];
            for (int j = 0; j < instrumentIdList.size(); j++) {
                topics[j] = new Topic(subAccountList.get(i), instrumentIdList.get(j));
            }
            SubscribeInfo subscribeInfo = new SubscribeInfo("subscribe", topics);
            subscribeInfos.add(subscribeInfo);
        }
        return subscribeInfos;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public List<String> getLoginAccountList() {
        return loginAccountList;
    }

    public void setLoginAccountList(List<String> loginAccountList) {
        this.loginAccountList = loginAccountList;
    }

    public List<String> getSubAccountList() {
        return subAccountList;
    }

    public void setSubAccountList(List<String> subAccountList) {
        this.subAccountList = subAccountList;
    }

    public List<String> getInstrumentIdList() {
        return instrumentIdList;
    }

    public void setInstrumentIdList(List<String> instrumentIdList) {
        this.instrumentIdList = instrumentIdList;
    }
}
