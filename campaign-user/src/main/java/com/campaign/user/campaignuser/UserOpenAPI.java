package com.campaign.user.campaignuser;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class UserOpenAPI {
    @Bean
    public OpenAPI userManagementOpenAPI(){
        return new OpenAPI()
                .info(new Info().title("User API")
                        .description("User Management for Campaign Management application")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation());

    }
    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener(){
        return new ValidatingMongoEventListener(getValidator());
    }
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        return new LocalValidatorFactoryBean();
    }
}
