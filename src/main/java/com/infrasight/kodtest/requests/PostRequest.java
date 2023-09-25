package com.infrasight.kodtest.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infrasight.kodtest.UserCredentials;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PostRequest extends Request {

    private String jsonRequest = "";

    public PostRequest(String user, String password) {
        super("");
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUser(user);
        userCredentials.setPassword(password);
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.jsonRequest = mapper.writeValueAsString(userCredentials);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public HttpResponse<String> request(String URL, String query) {
        HttpResponse<String> response = null;
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(URL + query))
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();
            response = HttpClient.newHttpClient().send(getRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}
