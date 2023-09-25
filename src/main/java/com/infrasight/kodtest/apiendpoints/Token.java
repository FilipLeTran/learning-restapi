package com.infrasight.kodtest.apiendpoints;

public class Token {
    private String token;

    public String getToken() {
        return "Bearer " + token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
