package com.infrasight.kodtest;

import java.net.http.HttpResponse;

public class HandleStatusCodes {

    public HandleStatusCodes() {}

    public boolean handleCode(int statusCode) throws InterruptedException {
        switch(statusCode) {
            case 200:
                return false;
            case 429:
                Thread.sleep(1000);
            default:
                return true;
        }
    }
}
