package com.tna.campus_store.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TokenMsg extends Msg {
    private String token;

    public static TokenMsg success(String msg, String token) {
        TokenMsg result = new TokenMsg();
        result.setCode(2000);
        result.setToken(token);
        result.setMsg(msg);
        return result;
    }
}
