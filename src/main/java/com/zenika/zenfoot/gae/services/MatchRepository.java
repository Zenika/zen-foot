package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.dao.MatchDAO;
import com.zenika.zenfoot.gae.model.Match;

import java.util.List;

/**
 * Created by raphael on 28/04/14.
 */
public class MatchRepository {

    protected MatchDAO matchDAO;

    public MatchRepository(MatchDAO matchDAO) {
        this.matchDAO = matchDAO;





        //TODO : create a cleaner method/class which initializes these data.
        //This shouldn't be done in the repository, as we'd like to expand our application to several kinds
        // of competitions

        //Delete every match before storing them in the database



        //deleteAll();



    }


    /*
    CRUD methods
     */



    public void createMatch2(Match match) {
        this.matchDAO.addMatch(match);
    }




    public void delete2(Long id) {
        this.matchDAO.deleteMatch(id);
    }




    public Match getMatch2(Long id) {
        return this.matchDAO.getMatch(id);
    }




    public List<Match> getAll2() {
        return matchDAO.getAll();
    }

    //TODO delete
    public void deleteAll(){
        matchDAO.deleteAll();
    }



}