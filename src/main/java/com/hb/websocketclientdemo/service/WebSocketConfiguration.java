package com.hb.websocketclientdemo.service;


import com.hb.websocketclientdemo.model.Info;
import com.hb.websocketclientdemo.model.LoginInfo;
import com.hb.websocketclientdemo.model.SubscribeInfo;
import com.hb.websocketclientdemo.model.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketConfiguration {

    @Value("${login.account1}")
    private String loginAccount;

    @Value("${login.account2}")
    private String loginAccount2;

    @Value("${login.account3}")
    private String loginAccount3;

    @Value("${login.account4}")
    private String loginAccount4;

    @Value("${subscribe.account1}")
    private String subAccount;

    @Value("${subscribe.account2}")
    private String subAccount2;

    @Value("${subscribe.account3}")
    private String subAccount3;

    @Value("${subscribe.account4}")
    private String subAccount4;

    @Value("${login.password}")
    private String password;

    @Value("${subscribe.instrumentID1}")
    private String instrumentID_1;
    @Value("${subscribe.instrumentID2}")
    private String instrumentID_2;
    @Value("${subscribe.instrumentID3}")
    private String instrumentID_3;
    @Value("${subscribe.instrumentID4}")
    private String instrumentID_4;

    @Value("${subscribe.instrumentID5}")
    private String instrumentID_5;
    @Value("${subscribe.instrumentID6}")
    private String instrumentID_6;
    @Value("${subscribe.instrumentID7}")
    private String instrumentID_7;
    @Value("${subscribe.instrumentID8}")
    private String instrumentID_8;

    @Value("${subscribe.instrumentID9}")
    private String instrumentID_9;
    @Value("${subscribe.instrumentID10}")
    private String instrumentID_10;
    @Value("${subscribe.instrumentID11}")
    private String instrumentID_11;
    @Value("${subscribe.instrumentID12}")
    private String instrumentID_12;


    @Bean(name = "LoginInfoWS1")
    public LoginInfo getLoginInfo() {
        return new LoginInfo("login", new Info(loginAccount, password));
    }

    @SuppressWarnings("all")
    @Bean(name = "SubscribeInfoWS1")
    public SubscribeInfo getSubscribeInfo() {
        Topic[] topics = new Topic[12];
        topics[0] = new Topic(subAccount, instrumentID_1);
        topics[1] = new Topic(subAccount, instrumentID_2);
        topics[2] = new Topic(subAccount, instrumentID_3);
        topics[3] = new Topic(subAccount, instrumentID_4);
        topics[4] = new Topic(subAccount, instrumentID_5);
        topics[5] = new Topic(subAccount, instrumentID_6);
        topics[6] = new Topic(subAccount, instrumentID_7);
        topics[7] = new Topic(subAccount, instrumentID_8);
        topics[8] = new Topic(subAccount, instrumentID_9);
        topics[9] = new Topic(subAccount, instrumentID_10);
        topics[10] = new Topic(subAccount, instrumentID_11);
        topics[11] = new Topic(subAccount, instrumentID_12);
        return new SubscribeInfo("subscribe", topics);
    }

    @Bean(name = "LoginInfoWS2")
    public LoginInfo getLoginInfoWS2(){
        return new LoginInfo("login", new Info(loginAccount2, password));
    }

    @SuppressWarnings("all")
    @Bean(name = "SubscribeInfoWS2")
    public SubscribeInfo getSubInfoWS2(){
        Topic[] topics = new Topic[12];
        topics[0] = new Topic(subAccount2, instrumentID_1);
        topics[1] = new Topic(subAccount2, instrumentID_2);
        topics[2] = new Topic(subAccount2, instrumentID_3);
        topics[3] = new Topic(subAccount2, instrumentID_4);
        topics[4] = new Topic(subAccount2, instrumentID_5);
        topics[5] = new Topic(subAccount2, instrumentID_6);
        topics[6] = new Topic(subAccount2, instrumentID_7);
        topics[7] = new Topic(subAccount2, instrumentID_8);
        topics[8] = new Topic(subAccount2, instrumentID_9);
        topics[9] = new Topic(subAccount2, instrumentID_10);
        topics[10] = new Topic(subAccount2, instrumentID_11);
        topics[11] = new Topic(subAccount2, instrumentID_12);
        return new SubscribeInfo("subscribe", topics);
    }

    @Bean(name = "LoginInfoWS3")
    public LoginInfo getLoginInfoWS3(){
        return new LoginInfo("login", new Info(loginAccount3, password));
    }

    @SuppressWarnings("all")
    @Bean(name = "SubscribeInfoWS3")
    public SubscribeInfo getSubInfoWS3(){
        Topic[] topics = new Topic[12];
        topics[0] = new Topic(subAccount3, instrumentID_1);
        topics[1] = new Topic(subAccount3, instrumentID_2);
        topics[2] = new Topic(subAccount3, instrumentID_3);
        topics[3] = new Topic(subAccount3, instrumentID_4);
        topics[4] = new Topic(subAccount3, instrumentID_5);
        topics[5] = new Topic(subAccount3, instrumentID_6);
        topics[6] = new Topic(subAccount3, instrumentID_7);
        topics[7] = new Topic(subAccount3, instrumentID_8);
        topics[8] = new Topic(subAccount3, instrumentID_9);
        topics[9] = new Topic(subAccount3, instrumentID_10);
        topics[10] = new Topic(subAccount3, instrumentID_11);
        topics[11] = new Topic(subAccount3, instrumentID_12);
        return new SubscribeInfo("subscribe", topics);
    }

    @Bean(name = "LoginInfoWS4")
    public LoginInfo getLoginInfoWS4(){
        return new LoginInfo("login", new Info(loginAccount4, password));
    }

    @SuppressWarnings("all")
    @Bean(name = "SubscribeInfoWS4")
    public SubscribeInfo getSubInfoWS4(){
        Topic[] topics = new Topic[12];
        topics[0] = new Topic(subAccount4, instrumentID_1);
        topics[1] = new Topic(subAccount4, instrumentID_2);
        topics[2] = new Topic(subAccount4, instrumentID_3);
        topics[3] = new Topic(subAccount4, instrumentID_4);
        topics[4] = new Topic(subAccount4, instrumentID_5);
        topics[5] = new Topic(subAccount4, instrumentID_6);
        topics[6] = new Topic(subAccount4, instrumentID_7);
        topics[7] = new Topic(subAccount4, instrumentID_8);
        topics[8] = new Topic(subAccount4, instrumentID_9);
        topics[9] = new Topic(subAccount4, instrumentID_10);
        topics[10] = new Topic(subAccount4, instrumentID_11);
        topics[11] = new Topic(subAccount4, instrumentID_12);
        return new SubscribeInfo("subscribe", topics);
    }



}
