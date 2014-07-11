package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.zenika.zenfoot.gae.model.GamblerRanking;

import java.util.List;


/**
 * Created by raphael on 11/06/14.
 */
public class RankingDAO {

    public Key<GamblerRanking> createUpdate(GamblerRanking gamblerRanking) {
        Key<GamblerRanking> key = ObjectifyService.ofy().save().entity(gamblerRanking).now();
        return key;
    }


    public GamblerRanking getRanking(Long id) {
        return ObjectifyService.ofy().load().type(GamblerRanking.class).id(id).now();
    }

    public GamblerRanking getRanking(Key<GamblerRanking> key) {
        return ObjectifyService.ofy().load().key(key).now();
    }

    public void deleteRanking(Long id) {
        ObjectifyService.ofy().delete().type(GamblerRanking.class).id(id).now();
    }

    public List<GamblerRanking> getAll() {
        List<GamblerRanking> toRet = ObjectifyService.ofy().load().type(GamblerRanking.class).order("-points").list();
        return toRet;
    }

    public GamblerRanking findByGambler(Long gamblerId) {
        List<GamblerRanking> rankings = ObjectifyService.ofy().load().type(GamblerRanking.class).filter("gamblerId", gamblerId).list();
        if (rankings == null || rankings.isEmpty()) {
            return null;
        }
        if (rankings.size() > 1) {
            throw new RuntimeException("Several rankings with email " + gamblerId);
        }

//        return ObjectifyService.ofy().load().type(Gambler.class).id(gamblers.get(0).getId()).now();

        return rankings.get(0);

    }
}
