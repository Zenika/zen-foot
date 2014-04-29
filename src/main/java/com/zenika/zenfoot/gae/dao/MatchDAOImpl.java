package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.zenika.zenfoot.gae.model.Match;

import java.util.List;

/**
 * Created by raphael on 29/04/14.
 */
public class MatchDAOImpl implements MatchDAO {

    public static Objectify ofy=OfyService.ofy();

    @Override
    public void addMatch(Match match) {
        ofy.save().entity(match).now();
    }

    @Override
    public Match getMatch(Long id) {
        return ofy.load().type(Match.class).id(id).now();
    }

    @Override
    public void deleteMatch(Long id) {
        ofy.delete().type(Match.class).id(id).now();
    }

    @Override
    public List<Match> getAll() {
        return ofy.load().type(Match.class).list();
    }

    @Override
    public void deleteAll() {
        List<Key<Match>> keys = ofy.load().type(Match.class).keys().list();
        ofy.delete().keys(keys).now();
    }


}
