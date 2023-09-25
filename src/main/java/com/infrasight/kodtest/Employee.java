package com.infrasight.kodtest;

import com.infrasight.kodtest.apiendpoints.Account;
import com.infrasight.kodtest.apiendpoints.Group;
import com.infrasight.kodtest.apiendpoints.RelationshipGroup;
import com.infrasight.kodtest.requests.RequestAccounts;
import com.infrasight.kodtest.requests.RequestGroups;
import com.infrasight.kodtest.requests.RequestRelationships;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Employee {

    private final List<Account> accounts;
    private final String token;

    public Employee(String id, String API_KEY) {
        token = API_KEY;
        this.accounts = new RequestAccounts(token).requestId(id);
    }

    public Employee(int employeeId, String API_KEY) {
        token = API_KEY;
        this.accounts = new RequestAccounts(token).requestEmployeeId(employeeId);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    private List<RelationshipGroup> getRelationships() {
        ArrayList<RelationshipGroup> relationshipList = new ArrayList<>();
        for (Account account : this.accounts) {
            relationshipList.addAll(new RequestRelationships(token)
                                    .requestMemberId(account.getId()));
        }
        return relationshipList;
    }

    private List<RelationshipGroup> getRelationships(String groupId, Set<String> groupIds) {
        List<RelationshipGroup> relationshipList = new ArrayList<>();
        List<RelationshipGroup> requestedRelationships = new RequestRelationships(token).requestMemberId(groupId);
        for (RelationshipGroup relationship : requestedRelationships) {
            if(groupIds.contains(relationship.getGroupId())) {
                break;
            }
            groupIds.add(relationship.getGroupId());
            relationshipList.add(relationship);
            relationshipList.addAll(getRelationships(relationship.getGroupId(), groupIds));
        }

        return relationshipList;
    }

    public List<Group> getAllGroups() {
        Set<String> uniqueGroupIds = new HashSet<>(){};
        List<Group> directGroups = getDirectGroups();
        List<RelationshipGroup> allRelationships = new ArrayList<>();

        for (Group group : directGroups) { // change directRelationships to directGroups
            allRelationships.addAll(getRelationships(group.getId(), uniqueGroupIds));
        }

        List<Group> allGroupsList = new ArrayList<>(directGroups);
        for (RelationshipGroup groupRelation : allRelationships) {
            allGroupsList.addAll(new RequestGroups(token).request(groupRelation.getGroupId()));
        }
        return allGroupsList;
    }

    public List<Group> getDirectGroups() {
        ArrayList<Group> groupList = new ArrayList<>();
        RequestGroups requestGroups = new RequestGroups(token);
        for (RelationshipGroup relationship : getRelationships()) {
            groupList.addAll(requestGroups.request(relationship.getGroupId()));
        }
        return groupList;
    }


}
