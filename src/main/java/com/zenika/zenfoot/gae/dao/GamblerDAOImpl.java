package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Work;
import com.zenika.zenfoot.gae.model.Gambler;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by raphael on 30/04/14.
 */
public class GamblerDAOImpl implements GamblerDAO {


    @Override
    public Key<Gambler> saveGambler(Gambler gambler) {
        Key<Gambler> key = OfyService.ofy().save().entity(gambler).now();
        return key;
    }


    @Override
    public Gambler getGambler(Long id) {
        return OfyService.ofy().load().type(Gambler.class).id(id).now();
    }

    @Override
    public Gambler getGambler(Key<Gambler> key) {
        return OfyService.ofy().load().key(key).now();
    }

    @Override
    public void deleteGambler(Long id) {
        OfyService.ofy().delete().type(Gambler.class).id(id).now();
    }

    @Override
    public List<Gambler> getAll() {
        return OfyService.ofy().load().type(Gambler.class).list();
    }

    @Override
    public void deleteAll() {
        List<Gambler> gamblers = getAll();
        for (Gambler gambler : gamblers) {
            deleteGambler(gambler.getId());
        }
    }


    /**
     * Returns the gambler corresponding to the given email, or null if no Gambler corresponds to this email
     *
     * @param email a string representation of a user email
     * @return the gambler ccorresponding to the email in param
     */
    @Override
    public Gambler getGamblerFromEmail(String email) {


        List<Gambler> gamblers = OfyService.ofy().load().type(Gambler.class).filter("email", email).limit(1).list();
        Logger logger = Logger.getLogger(GamblerDAOImpl.class.getName());

        Gambler toRet = null;
        if (gamblers == null) logger.log(Level.SEVERE, "No gambler found with email " + email);
        if (gamblers != null && gamblers.size() > 0) {
            System.out.println("looking for " + email);
            System.out.println(gamblers.size() + " gamblers found");
            toRet = gamblers.get(0);
        } else {
            logger.log(Level.SEVERE, "No gambler found with email " + email);
        }
        return toRet;
    }


}
