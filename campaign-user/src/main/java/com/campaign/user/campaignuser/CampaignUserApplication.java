package com.campaign.user.campaignuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CampaignUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampaignUserApplication.class, args);
	}

}
