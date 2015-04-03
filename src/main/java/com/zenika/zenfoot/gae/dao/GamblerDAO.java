package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.IGenericDAO;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Team;

import java.util.List;

/**
 * Created by raphael on 30/04/14.
 */
public interface GamblerDAO extends IGenericDAO<Gambler> {
    Gambler getGamblerFromEmailAndEvent(String email, Event parent);

    List<Gambler> gamblersWannaJoin(String name);

    int nbGamblersInTeam(Team team);
    
    List<Bet> getBets(Gambler gambler);
    
    Key<Gambler> createUpdate(Gambler gambler);
}
