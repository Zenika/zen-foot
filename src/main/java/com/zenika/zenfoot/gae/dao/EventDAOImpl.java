package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.ObjectifyService;
import com.zenika.zenfoot.gae.GenericDAO;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Match;

import java.util.List;

/**
 * Created by raphael on 25/08/14.
 */
public class EventDAOImpl extends GenericDAO<Event> implements EventDAO {

    @Override
    public List<Match> getMatches(Event parent) {
        return ObjectifyService.ofy().load().type(Match.class).ancestor(parent).list();
    }

}
