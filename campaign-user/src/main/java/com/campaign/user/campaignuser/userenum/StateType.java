package com.campaign.user.campaignuser.userenum;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StateType {
    ACTIVE("Active"),
    SUSPENDED("Suspended"),
    TERMINATED("Terminated");

    private String type;

    StateType(String type) {

        this.type = type;
    }


    @JsonValue
    public String getType() {
        return type;
    }
}
