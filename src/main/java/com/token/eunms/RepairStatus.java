package com.token.eunms;

public enum RepairStatus {
    UN_SUBMITTED("0", "未提交"),
    WAITING_FOR_REVIEW("1", "待受理"),
    ASSIGNED("2", "已派工"),
    REPAIRED("3", "维修结束");

    private final String code;
    private final String description;

    RepairStatus(String code, String description) {
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

