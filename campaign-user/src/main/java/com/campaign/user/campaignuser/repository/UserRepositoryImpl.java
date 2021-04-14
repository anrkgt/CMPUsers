package com.campaign.user.campaignuser.repository;

import com.campaign.user.campaignuser.entity.User;
import com.campaign.user.campaignuser.template.TemplateMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl  {
    @Autowired
    private TemplateMongo templateMongo;

    public UserRepositoryImpl(TemplateMongo templateMongo) {
        this.templateMongo = templateMongo;
    }

    public User save(User entity) {
        templateMongo.getTemplateMongo().save(entity);
        return entity;
    }

    public User findById(String id) {
        return templateMongo.getTemplateMongo().findById(id, User.class);
    }

    public User remove(User user) {
        templateMongo.getTemplateMongo().remove(user);
        return  user;
    }

    public List<User> findAll() {
        return templateMongo.getTemplateMongo().findAll(User.class);
    }
}
