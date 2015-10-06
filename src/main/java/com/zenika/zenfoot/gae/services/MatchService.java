package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.dto.MatchDTO;
import com.zenika.zenfoot.gae.mapper.MapperFacadeFactory;
import com.zenika.zenfoot.gae.model.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zenika.zenfoot.gae.dao.MatchDAO;
import com.zenika.zenfoot.gae.model.Match;

public class MatchService extends AbstractGenericService<Match, Long> {
    private MapperFacadeFactory mapper;

    public MatchService(MatchDAO matchDAO, MapperFacadeFactory mapper) {
        super(matchDAO);
        this.mapper = mapper;
    }
    
    public Match getMatch(Long id, Event event) {
        return ((MatchDAO)this.getDao()).get(id, event);
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
