package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.model.Match;

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
}
