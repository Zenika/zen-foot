package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.StatutTeam;
import com.zenika.zenfoot.gae.model.Team;

import java.util.List;
import java.util.Set;

/**
 * Created by raphael on 30/04/14.
 */
public class GamblerDAOImpl extends GenericDAO<Gambler> implements GamblerDAO {


    @Override
    public Key<Gambler> saveGambler(Gambler gambler) {
        
        Key<Gambler> key = ObjectifyService.ofy().save().entity(gambler).now();
        return key;
    }

    /**
     * We have to register teams before registering a gambler, in order to have their ID generated.
     *
     * @param statutTeams
     */
    private void registerTeams(Set<StatutTeam> statutTeams) {
        for (StatutTeam statutTeam : statutTeams) {
            ObjectifyService.ofy().save().entity(statutTeam.getTeam());

        }
    }

    @Override
    public void deleteAll() {
        List<Gambler> gamblers = getAll();
        for (Gambler gambler : gamblers) {
            delete(gambler.getId());
        }
    }

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
    public int nbGamblersInTeam(Team team){
        List<Gambler> gamblers = ObjectifyService.ofy().load().type(Gambler.class).filter("statutTeams.team.name", team.getName()).list();
        int number = 0;

        for(Gambler gambler : gamblers){
            StatutTeam statutTeam = gambler.getStatutTeam(team.getId());
            if((statutTeam!=null) && (statutTeam.isAccepted() || gambler.isOwner(statutTeam))){
                number ++;
            }
        }

        return number;
    }

    @Override
    public List<Bet> getBets(Gambler parent) {
        return ObjectifyService.ofy().load().type(Bet.class).ancestor(parent).list();
    }


}
