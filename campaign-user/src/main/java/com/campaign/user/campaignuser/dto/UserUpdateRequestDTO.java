package com.campaign.user.campaignuser.dto;

import com.campaign.user.campaignuser.constants.ErrorConstants;
import com.campaign.user.campaignuser.constraint.EnumConstraint;
import com.campaign.user.campaignuser.userenum.StateType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserUpdateRequestDTO {
    @Pattern(regexp = "^\\d{10}$", message = ErrorConstants.PHONE_NUMBER_WITH_10_DIGITS)
    private String phoneNumber;

    @Email()
    private String email;

    @Min(value = 15, message = ErrorConstants.AGE_LIMIT_SHOULD_BE_MORE_THAN_15)
    @Max(value = 90, message = ErrorConstants.AGE_LIMIT_CANNOT_BE_MORE_THAN_90)
    private int age;

    private String address;

    @EnumConstraint(
            acceptedValues = "Active | Terminated | Suspended",
            enumClass = StateType.class,
            message = ErrorConstants.VALID_STATE
    )
    private String state;
}
