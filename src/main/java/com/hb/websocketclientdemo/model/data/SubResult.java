package com.hb.websocketclientdemo.model.data;

import com.hb.websocketclientdemo.model.Topic;
import org.springframework.stereotype.Component;

@Component
public class SubResult {
    private String result;
    private Topic[] topics;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Topic[] getTopics() {
        return topics;
    }

    public void setTopics(Topic[] topics) {
        this.topics = topics;
    }
}
