package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.zenika.zenfoot.gae.model.Event;

import java.util.List;

/**
 * Created by raphael on 25/08/14.
 */
public class EventDAO {

    public Key<Event> save(Event event){
        return ObjectifyService.ofy().save().entity(event).now();
    }

    public List<Event> getAll(){
        return ObjectifyService.ofy().load().type(Event.class).list();
    }

}
