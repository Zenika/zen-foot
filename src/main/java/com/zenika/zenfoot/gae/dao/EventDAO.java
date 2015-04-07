package com.zenika.zenfoot.gae.dao;

import java.util.List;

import com.zenika.zenfoot.gae.IGenericDAO;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Match;

/**
 * Created by raphael on 25/08/14.
 */
public interface EventDAO extends IGenericDAO<Event>{

    List<Match> getMatches(Event parent);
    List<Gambler> getGamblers(Event parent);

}
