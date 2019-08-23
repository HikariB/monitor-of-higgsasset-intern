package com.hb.websocketclientdemo.model.loginAndSubscribe;

public class Topic {
    private String account;
    private String instrument;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }


    public Topic(String account, String instrument) {
        this.account = account;
        this.instrument = instrument;
    }
}
