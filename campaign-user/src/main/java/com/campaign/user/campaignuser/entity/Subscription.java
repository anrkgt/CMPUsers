package com.campaign.user.campaignuser.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    private String Id;

    private String campaignId;

    private LocalDate startDate;

    private LocalDate endDate;

    
}
