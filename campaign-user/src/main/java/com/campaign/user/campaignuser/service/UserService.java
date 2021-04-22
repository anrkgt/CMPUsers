package com.campaign.user.campaignuser.service;

import com.campaign.user.campaignuser.entity.User;
import com.campaign.user.campaignuser.repository.UserRepositoryImpl;
import com.campaign.user.campaignuser.userenum.StateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class UserService {
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private SequenceGenerator sequenceGenerator;

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
        setAndSaveUser(user, existingUser.getName());
    }

    private User setAndSaveUser(User user, String name) {
        user.setName(name);
        return this.userRepositoryImpl.save(user);
    }

    public User suspendUser(User user) {
        canSuspend(user);
        user.setState(StateType.SUSPENDED.getType());
        return setAndSaveUser(user, user.getName());
    }

    public User activateUser(User user) {
        canActivate(user);
        user.setState(StateType.ACTIVE.getType());
        return setAndSaveUser(user, user.getName());
    }

    private void canSuspend(User existingUser) {
        Predicate<String> canBeSuspended = state -> state.equalsIgnoreCase(StateType.ACTIVE.toString());
        if(canBeSuspended.negate().test(existingUser.getState())) {
            throw new IllegalArgumentException("Cannot suspend, state of user is already Suspended");
        }
    }

    private void canActivate(User existingUser) {
        Predicate<String> canBeActivated = state -> state.equalsIgnoreCase(StateType.SUSPENDED.toString());
        if(canBeActivated.negate().test(existingUser.getState())) {
            throw new IllegalArgumentException("Cannot activate, state of user is already Active or maybe Terminated");
        }
    }
}
