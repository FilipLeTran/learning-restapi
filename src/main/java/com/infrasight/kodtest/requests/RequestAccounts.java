package com.infrasight.kodtest.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infrasight.kodtest.HandleStatusCodes;
import com.infrasight.kodtest.URL;
import com.infrasight.kodtest.apiendpoints.Account;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public class RequestAccounts {

    private final String API_KEY;

    public RequestAccounts(String token) {
        this.API_KEY = token;
    }

    private List<Account> request(String query) {
        boolean waitForToken = true;
        List<Account> accounts = null;
        HttpResponse<String> response;
        while (waitForToken) {
            try {
                response = new GetRequest(API_KEY).request(URL.ACCOUNT.getUrl(), query);
                waitForToken = new HandleStatusCodes().handleCode(response.statusCode());
                accounts = new ObjectMapper().readValue(response.body(), new TypeReference<>(){});
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        return accounts;
    }

    public List<Account> requestEmployeeId(int employeeId) {
        String query = "?filter=employeeId%3D" + employeeId;
        return request(query);
    }

    public List<Account> requestId(String id) {
        String query = id;
        if (!query.isEmpty()) {
            query = "?filter=id%3D" + id;
        }
        return request(query);
    }
}
