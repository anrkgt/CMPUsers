package com.campaign.user.campaignuser.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorMessage {
    private List<String> errors;

    public ErrorMessage(List<String> errors) {
        this.errors = errors;
    }
}
