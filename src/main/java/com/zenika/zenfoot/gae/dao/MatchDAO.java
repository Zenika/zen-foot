package com.zenika.zenfoot.gae.dao;

import com.zenika.zenfoot.gae.IGenericDAO;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Match;


/**
 * Created by raphael on 29/04/14.
 */
public interface MatchDAO extends IGenericDAO<Match> {
    Match get(Long id, Event event);
}
