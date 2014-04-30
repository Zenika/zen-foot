package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Objectify;
import com.zenika.zenfoot.gae.model.Gambler;

import java.util.List;

/**
 * Created by raphael on 30/04/14.
 */
public class GamblerDAOImpl implements GamblerDAO{

    private static Objectify ofy=OfyService.ofy();

    @Override
    public void addGambler(Gambler gambler) {
        ofy.save().entity(gambler);
    }

    @Override
    public Gambler getGambler(Long id) {
        return ofy.load().type(Gambler.class).id(id).now();
    }

    @Override
    public void deleteGambler(Long id) {
        ofy.delete().type(Gambler.class).id(id).now();
    }

    @Override
    public List<Gambler> getAll() {
        return ofy.load().type(Gambler.class).list();
    }

    @Override
    public void deleteAll() {
        List<Gambler> gamblers =getAll();
        for(Gambler gambler:gamblers){
            deleteGambler(gambler.getId());
        }
    }
}
