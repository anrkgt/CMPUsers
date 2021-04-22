package com.campaign.user.campaignuser.dto;

import com.campaign.user.campaignuser.constants.ErrorConstants;
import com.campaign.user.campaignuser.constraint.EnumConstraint;
import com.campaign.user.campaignuser.userenum.StateType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStateRequestDTO {
    @EnumConstraint(
            acceptedValues = "Active | Terminated | Suspended",
            enumClass = StateType.class,
            message = ErrorConstants.VALID_STATE
    )
    private String state;
}
