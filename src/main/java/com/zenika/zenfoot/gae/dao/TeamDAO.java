package com.zenika.zenfoot.gae.dao;

import com.google.common.base.Optional;
import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.model.Team;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by raphael on 03/06/14.
 */
public class TeamDAO {

    public Key<Team> createUpdate(Team team) {
        return OfyService.ofy().save().entity(team).now();
    }

    public Team get(Long id) {
        return OfyService.ofy().load().type(Team.class).id(id).now();
    }

    public Team get(Key<Team> key) {
        return OfyService.ofy().load().key(key).now();
    }


    public List<Team> getAll() {
        return OfyService.ofy().load().type(Team.class).list();
    }

    public Optional<Team> get(String name){
        List<Team> team =  OfyService.ofy().load().type(Team.class).filter("name",name).limit(1).list();
        Logger logger = Logger.getLogger(TeamDAO.class.getName());

        Team toRet = null;
        if(team.size()>1) {
            logger.log(Level.INFO, "Objectify fetch for a team by name found more than one Team");
        }
        else{
            if(team.size()>0){
                toRet = team.get(0);
            }
            else{

            }
        }
        return Optional.fromNullable(toRet);
    }

}