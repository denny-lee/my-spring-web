package com.lee.ws;

public class OutMsg {
    private String text;
    private String topic;

    public OutMsg() {}
    public OutMsg(String text, String topic) {
        this.text = text;
        this.topic = topic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
