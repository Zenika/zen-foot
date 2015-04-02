package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Team;

import java.util.List;

/**
 * Created by raphael on 30/04/14.
 */
public interface GamblerDAO {

    com.googlecode.objectify.Key<Gambler> saveGambler(Gambler gambler);

    Gambler get(Long id);

    Gambler get(Key<Gambler> key);


    void delete(Long id);

    List<Gambler> getAll();

    //TODO delete
    void deleteAll();

    Gambler getGamblerFromEmailAndEvent(String email, Event parent);


    List<Gambler> gamblersWannaJoin(String name);

    int nbGamblersInTeam(Team team);
    
    List<Bet> getBets(Gambler gambler);
    
    Key<Gambler> createUpdate(Gambler gambler);
}
