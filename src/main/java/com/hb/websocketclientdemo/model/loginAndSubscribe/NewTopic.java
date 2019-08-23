package com.hb.websocketclientdemo.model.loginAndSubscribe;

import com.alibaba.fastjson.annotation.JSONField;

public class NewTopic {
    @JSONField(name = "instrument_id")
    private String instrumentId;
    @JSONField(name = "product_id")
    private String productId;
    @JSONField(name = "product_class")
    private String productClass;

    public NewTopic(String instrumentId, String productId, String productClass) {
        this.instrumentId = instrumentId;
        this.productId = productId;
        this.productClass = productClass;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductClass() {
        return productClass;
    }

    public void setProductClass(String productClass) {
        this.productClass = productClass;
    }
}
