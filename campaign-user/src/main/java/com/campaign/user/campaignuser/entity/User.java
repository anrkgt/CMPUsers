package com.campaign.user.campaignuser.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
public class User {
    @Schema(description = "Unique identifier for User", required = true)
    @Id
    private String id;

    @Schema(description = "Name of User", required = true)
    private String name;

    @Schema(description = "Phone number of User", required = true, maxLength = 10)
    private String phoneNumber;

    @Schema(description = "Email of User", required = true)
    private String email;

    @Schema(description = "Age of User", required = true)
    private int age;

    @Schema(description = "Address of User", required = false)
    private String address;

    @Schema(description = "Status can be Active, Suspended, Terminated", required = true)
    private String state;

    public User(String name, String phone){
        this.name = name;
        this.phoneNumber = phone;
    }

    public User(String name) {
        this.name = name;
    }

}
