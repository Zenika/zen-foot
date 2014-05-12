package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.zenika.zenfoot.gae.model.Gambler;

import java.util.List;

/**
 * Created by raphael on 30/04/14.
 */
public class GamblerDAOImpl implements GamblerDAO{

    private static Objectify ofy=OfyService.ofy();

    @Override
    public Key<Gambler> saveGambler(Gambler gambler) {
        Key<Gambler> key= ofy.save().entity(gambler).now();
        return key;
    }



    @Override
    public Gambler getGambler(Long id) {
        return ofy.load().type(Gambler.class).id(id).now();
    }

    @Override
    public Gambler getGambler(Key<Gambler> key) {
        return ofy.load().key(key).now();
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


    /**
     * Returns the gambler corresponding to the given email, or null if no Gambler corresponds to this email
     * @param email a string representation of a user email
     * @return the gambler ccorresponding to the email in param
     */
    @Override
    public Gambler getGamblerFromEmail(String email) {
        List<Gambler> gamblers = ofy.load().type(Gambler.class).filter("email",email).limit(1).list();

        Gambler toRet=null;
        if(gamblers.size()>0) {
            System.out.println("looking for "+email);
            System.out.println(gamblers.size()+" gamblers found");
            toRet= gamblers.get(0);
        }
        else
        {
            System.out.println("------------------------------------");
            System.out.println("no gambler found with email "+email);
        }
        return toRet;
    }
}
