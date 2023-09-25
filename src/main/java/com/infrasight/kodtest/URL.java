package com.infrasight.kodtest;

public enum URL {
    AUTHENTICATION("http://localhost:8081/api/auth"),
    GROUP("http://localhost:8081/api/groups"),
    RELATIONSHIP("http://localhost:8081/api/relationships"),
    ACCOUNT("http://localhost:8081/api/accounts");

    private String url;

    URL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
