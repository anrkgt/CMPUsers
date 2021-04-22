package com.campaign.user.campaignuser.dto;

import com.campaign.user.campaignuser.entity.CampaignAggregated;
import lombok.Data;

import java.util.List;

@Data
public class CampaignsDTO {
    List<CampaignAggregated> campaignList;
}
