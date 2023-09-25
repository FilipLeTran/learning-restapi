package com.infrasight.kodtest.requests;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infrasight.kodtest.HandleStatusCodes;
import com.infrasight.kodtest.URL;
import com.infrasight.kodtest.apiendpoints.Token;

import java.io.IOException;
import java.net.http.HttpResponse;

public class RequestToken {

    private final String user;
    private final String password;

    public RequestToken(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String request(String query) throws JsonProcessingException {
        HttpResponse<String> response;
        String token = null;
        boolean waitForToken = true;
        while (waitForToken) {
            try {
                response = new PostRequest(user, password).request(URL.AUTHENTICATION.getUrl(), query);
                waitForToken = new HandleStatusCodes().handleCode(response.statusCode());
                token = new ObjectMapper().readValue(response.body(), Token.class).getToken();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return token;
    }
}
