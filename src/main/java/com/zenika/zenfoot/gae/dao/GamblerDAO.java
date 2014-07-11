package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Team;

import java.util.List;

/**
 * Created by raphael on 30/04/14.
 */
public interface GamblerDAO {

    com.googlecode.objectify.Key<Gambler> saveGambler(Gambler gambler);

    Gambler getGambler(Long id);

    Gambler getGambler(Key<Gambler> key);


    void deleteGambler(Long id);

    List<Gambler> getAll();

    //TODO delete
    void deleteAll();

    Gambler getGamblerFromEmail(String email);


    List<Gambler> gamblersWannaJoin(String name);

    int nbGamblersInTeam(Team team);
}
