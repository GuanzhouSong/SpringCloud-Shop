package com.kedacom.keda.model;

public class TokenModel {
    /**
     * User id
     */
    private long userId;

    /**
     * Randomly Generated uuid
     */
    private String token;

    public TokenModel(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public long getUserId () {
        return userId;
    }

    public void setUserId (long userId) {
        this.userId = userId;
    }

    public String getToken () {
        return token;
    }

    public void setToken (String token) {
        this.token = token;
    }
}
