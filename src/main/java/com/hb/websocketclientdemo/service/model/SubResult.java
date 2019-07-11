package com.hb.websocketclientdemo.service.model;

import com.hb.websocketclientdemo.model.Topic;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class SubResult {
    private String result;
    private List<Topic> topics;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        return "SubResult{" +
                "result='" + result + '\'' +
                ", topics=" + topics +
                '}';
    }

    public SubResult() {
        this.result = "";
        this.topics = new LinkedList<>();
    }
}
