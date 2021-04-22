package com.campaign.user.campaignuser.dto;

import com.campaign.user.campaignuser.entity.Subscription;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUserResponseDTO {
    private String id;

    private String name;

    private String phoneNumber;

    private String email;

    private int age;

    private String address;

    private String state;

    List<Subscription> subscriptions;
}
