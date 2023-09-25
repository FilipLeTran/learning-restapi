package com.infrasight.kodtest.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infrasight.kodtest.HandleStatusCodes;
import com.infrasight.kodtest.URL;
import com.infrasight.kodtest.apiendpoints.Group;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public class RequestGroups {
    private final String API_KEY;
    public RequestGroups(String token) {
        this.API_KEY = token;
    }

    public List<Group> request(String groupId) {
        boolean waitForToken = true;
        HttpResponse<String> response;
        String query = groupId;
        List<Group> groups = null;
        if(!query.isEmpty()){
            query = "?filter=id%3D" + query;
        }
        while (waitForToken) {
            try {
                response = new GetRequest(API_KEY).request(URL.GROUP.getUrl(), query);
                waitForToken = new HandleStatusCodes().handleCode(response.statusCode());
                groups = new ObjectMapper().readValue(response.body(), new TypeReference<>(){});
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return groups;
    }
}
