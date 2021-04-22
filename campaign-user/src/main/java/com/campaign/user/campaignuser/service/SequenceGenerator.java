package com.campaign.user.campaignuser.service;

import com.campaign.user.campaignuser.entity.Sequence;
import com.campaign.user.campaignuser.template.TemplateMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class SequenceGenerator {
    @Autowired
    private TemplateMongo templateMongo;

    public int getSequence(String sequenceName) {
        Query query = new Query(Criteria.where("id").is(sequenceName));
        Update update = new Update().inc("sequence", 1);
        Sequence sequence = templateMongo.getTemplateMongo().findAndModify
                (query, update, options().returnNew(true).upsert(true), Sequence.class);
        return !Objects.isNull(sequence) ? sequence.getSequence() : 987 ;
    }
}
