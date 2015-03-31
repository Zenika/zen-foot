package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Match;

import java.util.List;

/**
 * Created by raphael on 25/08/14.
 */
public interface EventDAO {

    Key<Event> createUpdate(Event event);

    List<Event> getAll();

    Event get(Long id);

    Event get(Key<Event> key);

    void delete(Long id);

    List<Match> getMatches(Event parent);

}
