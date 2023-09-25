package com.infrasight.kodtest.apiendpoints;

import com.infrasight.kodtest.apiendpoints.Relationship;

public class RelationshipManage extends Relationship {
    private String managedId, accountId;


    public String getManagedId() {
        return managedId;
    }

    public void setManagedId(String managedId) {
        this.managedId = managedId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
