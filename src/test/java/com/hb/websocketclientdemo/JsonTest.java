package com.hb.websocketclientdemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.websocketclientdemo.model.Info;
import com.hb.websocketclientdemo.model.LoginInfo;
import com.hb.websocketclientdemo.model.SubscribeInfo;
import com.hb.websocketclientdemo.model.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

public class JsonTest {

//    private static final Logger logger = LoggerFactory.getLogger(JsonTest.class);

    @Autowired
    private static Info info;


    public static void main(String[] args) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
//        Info info = new Info("higgs1","higgspass");
//        LoginInfo loginInfo = new LoginInfo("login",info);
//
//
//
//        String str = mapper.writeValueAsString(loginInfo);
//        System.out.println(str);
//        String str1 = mapper.writeValueAsString(info);
//        System.out.println(str1);



        SubscribeInfo subscribeInfo = new SubscribeInfo(null,new Topic[3]);
        subscribeInfo.setChannel("subscribe");
        subscribeInfo.getTopics()[0] = new Topic("83925087","IF1904");
        subscribeInfo.getTopics()[1] = new Topic("83925087","IF1905");
        subscribeInfo.getTopics()[2] = new Topic("83925087","IF1906");


        String str2 = mapper.writeValueAsString(subscribeInfo);
        System.out.println(str2);

        String str3 = "{\"channel\":\"subscribe\", \"topics\":[{\"account\":\"83925087\", \"instrument\":\"IF1904\"}, {\"account\":\"83925087\", \"instrument\":\"IF1905\"}, {\"account\":\"83925087\", \"instrument\":\"IF1906\"}]}";

        System.out.println(str3);

        JSONObject jsonObject = JSONObject.parseObject(str3);
        System.out.println(jsonObject.get("channel"));

//        logger.info("test for logger");
    }
}
/**
 * 在对象类上添加@JsonInclude(Include.NON_NULL) 配置可以不转换null的属性
 */
/*
@Bean//使用@Bean注入fastJsonHttpMessageConvert
public HttpMessageConverters fastJsonHttpMessageConverters(){
//1.需要定义一个Convert转换消息的对象
    FastJsonHttpMessageConverter fastConverter=new FastJsonHttpMessageConverter();
//2.添加fastjson的配置信息，比如是否要格式化返回的json数据
    FastJsonConfig fastJsonConfig=new FastJsonConfig();
//SerializerFeature.WriteMapNullValue序列化null的字段
    fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//3.在convert中添加配置信息
    fastConverter.setFastJsonConfig(fastJsonConfig);
    HttpMessageConverter<?> converter=fastConverter;
    return new HttpMessageConverters(converter);
}
*/