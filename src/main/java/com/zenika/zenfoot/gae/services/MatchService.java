package com.zenika.zenfoot.gae.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zenika.zenfoot.gae.dao.MatchDAO;
import com.zenika.zenfoot.gae.model.Match;

public class MatchService {

    private MatchDAO matchDAO;

    public MatchService(MatchDAO matchDAO) {
        this.matchDAO = matchDAO;
    }

    public void createUpdate(Match match) {
        this.matchDAO.createUpdate(match);
    }


    public void delete(Long id) {
        this.matchDAO.deleteMatch(id);
    }


    public Match getMatch(Long id) {
        return this.matchDAO.getMatch(id);
    }


    public List<Match> getAll() {
        return matchDAO.getAll();
    }

    public void deleteAll() {
        matchDAO.deleteAll();
    }

    //TODO register name of countries in DB
    public List<String> getPays(){
        String[]pays = {  "croatie","bresil",
           "cameroun","mexique",
           "espagne","paysBas",
           "chili","australie",
           "colombie","grece",
           "uruguay","costaRica",
           "angleterre","italie",
           "coteIvoir","japon",
           "suisse","equateur",
           "honduras","france",
           "argentine","bosnie",
           "allemagne","portugal",
           "iran","nigeria",
           "ghana","usa",
           "belgique","algerie",
           "russie",
           "coree"};
        ArrayList<String> paysToRet = new ArrayList<>(Arrays.asList(pays));
        return paysToRet;
    }
}
