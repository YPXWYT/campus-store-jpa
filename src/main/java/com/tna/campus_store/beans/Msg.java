package com.tna.campus_store.beans;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回的类
 *
 * @author lfy
 */
@Data
public class Msg {
    private int code;
    private String msg;

    private Map<String, Object> data = new HashMap<String, Object>();

    public static Msg success(String msg) {
        Msg result = new Msg();
        result.setCode(2000);
        result.setMsg(msg);
        return result;
    }

    public static Msg fail(String msg, Integer code) {
        Msg result = new Msg();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public Msg add(String key, Object value) {
        this.getData().put(key, value);
        return this;
    }
}
