package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.dao.MatchDAO;
import com.zenika.zenfoot.gae.dto.MatchDTO;
import com.zenika.zenfoot.gae.mapper.MapperFacadeFactory;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchService {

    private MatchDAO matchDAO;
    private MapperFacadeFactory mapper;

    public MatchService(MatchDAO matchDAO, MapperFacadeFactory mapper) {
        this.matchDAO = matchDAO;
        this.mapper = mapper;
    }

    public void createUpdate(MatchDTO match) {
        Match m = mapper.getMapper().map(match, Match.class);
        this.matchDAO.createUpdate(m);
    }

    public void createUpdate(Match match) {
        this.matchDAO.createUpdate(match);
    }


    public void delete(Long id) {
        this.matchDAO.deleteMatch(id);
    }


    public Match getMatch(Long id, Event event) {
        return this.matchDAO.getMatch(id, event);
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
