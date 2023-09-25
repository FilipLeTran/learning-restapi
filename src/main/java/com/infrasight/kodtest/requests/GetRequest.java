package com.infrasight.kodtest.requests;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetRequest extends Request {

    public GetRequest(String API_KEY) {
        super(API_KEY);
    }

    public HttpResponse<String> request(String URL, String query) {
        HttpResponse<String> response = null;
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(URL + query))
                    .header("Authorization", API_KEY)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            response = HttpClient.newHttpClient().send(getRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}
