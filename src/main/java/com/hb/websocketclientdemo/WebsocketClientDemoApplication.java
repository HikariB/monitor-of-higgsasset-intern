package com.hb.websocketclientdemo;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;


@SpringBootApplication
public class WebsocketClientDemoApplication {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(WebsocketClientDemoApplication.class, args);
    }
//fastjackson
//    @Bean//使用@Bean注入fastJsonHttpMessageConvert
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
////1.需要定义一个Convert转换消息的对象
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
////2.添加fastjson的配置信息，比如是否要格式化返回的json数据
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
////SerializerFeature.WriteMapNullValue序列化null的字段
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
////3.在convert中添加配置信息
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        HttpMessageConverter<?> converter = fastConverter;
//        return new HttpMessageConverters(converter);
//    }
}
