package com.tna.campus_store.beans;

public enum StatusEnum {
    HINT(2001), AUTH_DUE(2002), UNAUTHORIZED(2001);

    private Integer code;

    private StatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
