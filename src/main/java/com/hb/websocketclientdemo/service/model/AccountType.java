package com.hb.websocketclientdemo.service.model;

public enum AccountType {
    UNKNOWN(0),
    STOCK_INDEX_FT(1),
    COMMODITY_FT(2),
    BOND_FT(3),
    OPT(4);

    public final int type;

    AccountType(int type) {
        this.type = type;
    }

    public static AccountType forType(int type) {
        switch (type) {
            case 1:
                return STOCK_INDEX_FT;
            case 2:
                return COMMODITY_FT;
            case 3:
                return BOND_FT;
            case 4:
                return OPT;
            default:
                return UNKNOWN;
        }
    }


    public int getType() {
        return type;
    }
}
