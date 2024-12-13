package com.token.eunms;

public enum LoginStatus {
    ONLINE("0", "在线"),
    LEAVE("1", "离开"),
    BUSY("2", "忙碌");

    private final String code;
    private final String value;

    LoginStatus(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
