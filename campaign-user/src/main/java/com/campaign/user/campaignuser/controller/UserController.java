package com.campaign.user.campaignuser.controller;

import com.campaign.user.campaignuser.dto.UserRequestDTO;
import com.campaign.user.campaignuser.dto.UserUpdateRequestDTO;
import com.campaign.user.campaignuser.entity.User;
import com.campaign.user.campaignuser.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/CPM/user")
@Tag(name = "Users", description = "User Management")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    @Operation(summary = "Create new users" , description = "API to create new users for CMP", method = "Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "200", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDTO user){
        ObjectMapper objectMapper = new ObjectMapper();
        User actualUser = objectMapper.convertValue(user, User.class);
        this.userService.saveUser(actualUser);
        return new ResponseEntity<>(actualUser.getId(), HttpStatus.OK);
    }

    @GetMapping("/getUser/{id}")
    @Operation(summary = "Get user by id" , description = "API to fetch user with a given id for CMP", method = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
    public ResponseEntity<User> getUser(@PathVariable("id") String id){
        return new ResponseEntity<>(userService.getUserDetails(id), HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    @Operation(summary = "Get all users" , description = "API to fetch all users for CMP", method = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<> (userService.findAllUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    @Operation(summary = "Delete specific user" , description = "API to delete user with given id for CMP", method = "Delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
    public void removeUser(@PathVariable("id") String id) {
        this.userService.deleteUser(id);
    }

    @PutMapping("/updateUser/{id}")
    @Operation(summary = "Update existing user" , description = "API to update user details for CMP", method = "Put")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "200", description = "Record updated successfully"),
            @ApiResponse(responseCode = "404", description = "Record not found"),
    })
    public void updateUser(@RequestBody UserUpdateRequestDTO userUpdateRequestDTO, @PathVariable("id") String id){
        User actualUser = new User();
        actualUser.setId(id);
        BeanUtils.copyProperties(userUpdateRequestDTO, actualUser);
        this.userService.updateUser(actualUser, id);
    }
}
