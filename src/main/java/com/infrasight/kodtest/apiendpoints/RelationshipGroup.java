package com.infrasight.kodtest.apiendpoints;

import com.infrasight.kodtest.apiendpoints.Relationship;

public class RelationshipGroup extends Relationship {

    private String groupId, memberId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

}
