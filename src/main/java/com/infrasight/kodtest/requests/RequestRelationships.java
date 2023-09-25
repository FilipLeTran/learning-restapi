package com.infrasight.kodtest.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infrasight.kodtest.HandleStatusCodes;
import com.infrasight.kodtest.URL;
import com.infrasight.kodtest.apiendpoints.RelationshipGroup;
import com.infrasight.kodtest.apiendpoints.RelationshipManage;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public class RequestRelationships {

    private final String API_KEY;
    private static int skips;
    private final int takeLimit = 50;
    public RequestRelationships(String token) {
        skips = 0;
        this.API_KEY = token;
    }

    private List<RelationshipGroup> requestGroup(String query) {
        HttpResponse<String> response;
        List<RelationshipGroup> relationships = null;
        boolean waitForToken = true;
        while (waitForToken) {
            try {
                response = new GetRequest(API_KEY).request(URL.RELATIONSHIP.getUrl(), query);
                if (response.statusCode() == 200) {
                    waitForToken = false;
                    relationships = new ObjectMapper().readValue(response.body(), new TypeReference<>(){});
                    if(relationships.size() == takeLimit) {
                        skips += 50;
                        relationships.addAll(takeMoreGroups(query));
                    }
                } else if (response.statusCode() == 429) {
                    Thread.sleep(1000);
                }
                // bring this out to parent class
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return relationships;
    }

    private List<RelationshipManage> requestManage(String query) {
        HttpResponse<String> response;
        List<RelationshipManage> relationships = null;
        boolean waitForToken = true;
        while (waitForToken) {
            try {
                response = new GetRequest(API_KEY).request(URL.RELATIONSHIP.getUrl(), query);
                waitForToken = new HandleStatusCodes().handleCode(response.statusCode());
                ObjectMapper mapper = new ObjectMapper();
                relationships = mapper.readValue(response.body(), new TypeReference<>() {});
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return relationships;
    }

    public List<RelationshipGroup> requestGroupId(String groupId) {
        String query = groupId;
        if(!query.isEmpty()) {
            query = "?filter=groupId%3D" + query;
        }
        return requestGroup(query);
    }

    public List<RelationshipGroup> requestMemberId(String memberId) {
        String query = memberId;
        if(!query.isEmpty()) {
            query = "?filter=memberId%3D" + query;
        }
        return requestGroup(query);
    }

    public List<RelationshipManage> requestManagedId(String memberId) {
        String query = memberId;
        if(!query.isEmpty()) {
            query = "?filter=managedId%3D" + query;
        }
        return requestManage(query);
    }

    public List<RelationshipManage> requestAccountId(String memberId) {
        String query = memberId;
        if(!query.isEmpty()) {
            query = "?filter=accountId%3D" + query;
        }
        return requestManage(query);
    }

    private List<RelationshipGroup> takeMoreGroups(String queryInput) {
        String query = queryInput;
        if(!query.isEmpty()) {
            query = query.replace(query.substring(0, 1), "");
            String newQuery = "?skip=" + skips;
            if(query.contains("&")){
                query = query.replace(query.substring(0, query.indexOf("&")+1), "");
            }

            query = newQuery + "&" + query;
        }
        return requestGroup(query);
    }
}
