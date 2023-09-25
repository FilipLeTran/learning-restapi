package com.infrasight.kodtest.requests;


import java.net.http.HttpResponse;

public abstract class Request {

    protected final String API_KEY;

    public Request(String API_KEY) {
        this.API_KEY = API_KEY;
    }

    abstract HttpResponse<String> request(String URL, String query);
}
