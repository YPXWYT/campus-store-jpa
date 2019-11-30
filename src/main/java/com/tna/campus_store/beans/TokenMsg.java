package com.tna.campus_store.beans;

public class TokenMsg extends Msg {
    private String token;

    public static TokenMsg success(String msg, String token) {
        TokenMsg result = new TokenMsg();
        result.setCode(2000);
        result.setToken(token);
        result.setMsg(msg);
        return result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
