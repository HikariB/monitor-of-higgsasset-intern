package com.hb.websocketclientdemo.model;

import com.hb.websocketclientdemo.model.loginAndSubscribe.NewTopic;

public class SubscribeJson {
    private String channel;

    private NewTopic[] topics;

    public SubscribeJson(String channel, NewTopic[] topics) {
        this.channel = channel;
        this.topics = topics;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public NewTopic[] getTopics() {
        return topics;
    }

    public void setTopics(NewTopic[] topics) {
        this.topics = topics;
    }
}
