package com.example.main.util;

public enum APICode {
    FAILED("0", "failed"),
    SUCCESS("1", "success"),;

    private final String code;
    private final String desc;

    private APICode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
