package com.zenika.zenfoot.gae.dao;

import com.google.common.base.Optional;
import com.zenika.zenfoot.gae.GenericDAO;
import com.zenika.zenfoot.gae.model.Team;

import java.util.List;
import java.util.logging.Level;

public class TeamDAOImpl extends GenericDAO<Team> implements TeamDAO {


    /**
     * Returns the optional of a team given its name
     * @param name the name of the team to find
     * @return the optional of the team corresponding to the name, an absent optional if no team is found 
     */
    public Optional<Team> get(String name) {
        List<Team> team = OfyService.ofy().load().type(Team.class).filter("name", name).limit(1).list();

        Team toRet = null;
        if (team.size() > 1) {
            logger.log(Level.INFO, "Objectify fetch for a team by name found more than one Team");
        } else {
            if (team.size() > 0) {
                toRet = team.get(0);
            } else {

            }
        }
        return Optional.fromNullable(toRet);
    }
}
