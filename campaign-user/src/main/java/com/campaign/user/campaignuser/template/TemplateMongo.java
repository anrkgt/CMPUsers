package com.campaign.user.campaignuser.template;

import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
@Profile("!test")
@Configuration
public class TemplateMongo {

    public MongoTemplate getTemplateMongo() {
        String connectionUrl = "mongodb://localhost:27017/CampaignUsers";
        return new MongoTemplate(MongoClients.create(String.format(connectionUrl)), "CampaignUsers");
    }

}
