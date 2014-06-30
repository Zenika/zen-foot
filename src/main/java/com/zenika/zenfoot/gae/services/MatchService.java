package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.model.Match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchService {

    private MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> getMatchs() {
        return matchRepository.getAll();
    }

    public Match getMatch(Long id) {
        return matchRepository.getMatch(id);
    }

    public void createUpdate(Match match) {
        this.matchRepository.createUpdate(match);
    }

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
