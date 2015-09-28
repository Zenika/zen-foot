package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.ObjectifyService;
import com.zenika.zenfoot.gae.GenericDAO;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Ligue;

import java.util.List;
import java.util.Set;

/**
 * Created by raphael on 30/04/14.
 */
public class GamblerDAOImpl extends GenericDAO<Gambler> implements GamblerDAO {

    /**
     * Returns the gambler corresponding to the given email, or null if no Gambler corresponds to this email
     *
     * @param email a string representation of a user email
     * @return the gambler ccorresponding to the email in param
     */
    @Override
    public Gambler getGamblerFromEmailAndEvent(String email, Event parent) {
        List<Gambler> gamblers = ObjectifyService.ofy().load().type(Gambler.class).ancestor(parent).filter("email", email).list();
        if (gamblers == null || gamblers.isEmpty()) {
            return null;
        }
        if (gamblers.size() > 1) {
            throw new RuntimeException("Several users with email " + email);
        }

        return gamblers.get(0);
    }


    @Override
    public List<Gambler> gamblersWannaJoin(String name) {
        List<Gambler> gamblers = ObjectifyService.ofy().load().type(Gambler.class).filter("statutTeams.team.name", name).list();
        return gamblers;
    }

    @Override
    public int nbGamblersInTeam(Ligue team){
        return 0;
    }

    @Override
    public List<Bet> getBets(Gambler parent) {
        return ObjectifyService.ofy().load().type(Bet.class).ancestor(parent).list();
    }


}
