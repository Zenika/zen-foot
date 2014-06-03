package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.model.Team;

import java.util.List;

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


}
