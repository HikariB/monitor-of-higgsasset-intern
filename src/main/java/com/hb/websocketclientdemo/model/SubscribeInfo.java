package com.hb.websocketclientdemo.model;

public class SubscribeInfo {
    private String channel;
    private Topic[] topics;

    public SubscribeInfo(String channel, Topic[] topics) {
        this.channel = channel;
        this.topics = topics;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Topic[] getTopics() {
        return topics;
    }

    public void setTopics(Topic[] topics) {
        this.topics = topics;
    }
}
