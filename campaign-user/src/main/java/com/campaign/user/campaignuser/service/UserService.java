package com.campaign.user.campaignuser.service;

import com.campaign.user.campaignuser.entity.User;
import com.campaign.user.campaignuser.repository.UserRepositoryImpl;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;

@Service
public class UserService {
    private UserRepositoryImpl userRepositoryImpl;

    public UserService(UserRepositoryImpl userRepositoryImpl) {
        this.userRepositoryImpl = userRepositoryImpl;
    }

    public User getUserDetails(String id) {
        User user = this.userRepositoryImpl.findById(id);
        return Optional.ofNullable(user).orElseThrow(() -> new IllegalArgumentException(String.format("User not found with id::%s", id)));
    }

    public List<User> findAllUsers() {
        return this.userRepositoryImpl.findAll();
    }

    public void saveUser(@Valid User user) {
        this.userRepositoryImpl.save(user);
        getUserDetails(user.getId());
    }

    public void deleteUser(String id) {
        User user =  getUserDetails(id);
        this.userRepositoryImpl.remove(user);
    }

    public void updateUser(User user, String id) {
        User existingUser =  getUserDetails(id);
        user.setName(existingUser.getName());
        this.userRepositoryImpl.save(user);
    }
}
