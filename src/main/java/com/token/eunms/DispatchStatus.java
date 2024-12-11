package com.token.eunms;

public enum DispatchStatus {
    UN_PROCESSED("0", "未处理"),
    PROCESSED("1", "已处理");

    private final String code;
    private final String description;

    DispatchStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

