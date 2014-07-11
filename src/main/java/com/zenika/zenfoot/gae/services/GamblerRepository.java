package com.zenika.zenfoot.gae.services;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.dao.GamblerDAO;
import com.zenika.zenfoot.gae.model.Gambler;

import java.util.List;

/**
 * Created by raphael on 30/04/14.
 */
public class GamblerRepository {


    private GamblerDAO gamblerDAO;

    public GamblerRepository(GamblerDAO gamblerDAO) {
        this.gamblerDAO = gamblerDAO;
    }


    public Gambler getGambler(Long id) {
        return gamblerDAO.getGambler(id);

    }

    public Gambler getGambler(Key<Gambler> key) {
        return gamblerDAO.getGambler(key);
    }

    public Gambler getGamblerFromEmail(String email) {
        return gamblerDAO.getGamblerFromEmail(email);
    }

    public Key<Gambler> saveGambler(Gambler gambler) {
        return gamblerDAO.saveGambler(gambler);
    }


    public List<Gambler> getAll() {
        return gamblerDAO.getAll();
    }

    public List<Gambler> wantToJoin(String name) {
        return gamblerDAO.gamblersWannaJoin(name);
    }
}
