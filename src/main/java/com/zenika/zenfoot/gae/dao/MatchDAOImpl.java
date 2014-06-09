package com.zenika.zenfoot.gae.dao;

import com.zenika.zenfoot.gae.model.Match;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by raphael on 29/04/14.
 */
public class MatchDAOImpl implements MatchDAO {



    @Override
    public void createUpdate(Match match) {
        OfyService.ofy().save().entity(match).now();
    }


    @Override
    public Match getMatch(Long id) {
        return OfyService.ofy().load().type(Match.class).id(id).now();
    }

    @Override
    public void deleteMatch(Long id) {
        OfyService.ofy().delete().type(Match.class).id(id).now();
    }

    @Override
    public List<Match> getAll() {
        return OfyService.ofy().load().type(Match.class).list();
    }

    @Override
    public void deleteAll() {
        Logger logger = Logger.getLogger(MatchDAOImpl.class.getName());
        List<Match> matchs = getAll();
        for(Match match : matchs){
            this.deleteMatch(match.getId());
        }
        logger.log(Level.WARNING,"deleted "+matchs.size()+ " matchs");
    }




}
