package com.zenika.zenfoot.gae.services;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.dao.EventDAO;
import com.zenika.zenfoot.gae.model.Event;

import java.util.List;

/**
 * Created by raphael on 28/08/14.
 */
public class EventService {

    private EventDAO eventDAO;

    public EventService(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    /**
     * Checks whether an event has already been registered with this name
     * @param eventName
     * @return
     */
    public boolean contains(String eventName){
        List<Event> events = eventDAO.getAll();
        boolean toRet = false;
        for(Event event : events){
            if(event.getName().equals(eventName)){
                toRet = true;
                break;
            }
        }
        return toRet;
    }

    public Key<Event> save(Event event){
        return eventDAO.save(event);
    }

    public List<Event> getAll() {
        return eventDAO.getAll();
    }
}
