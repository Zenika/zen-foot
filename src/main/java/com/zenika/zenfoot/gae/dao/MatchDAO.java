package com.zenika.zenfoot.gae.dao;

import com.zenika.zenfoot.gae.model.Match;

import java.util.List;

/**
 * Created by raphael on 29/04/14.
 */
public interface MatchDAO {

    void createUpdate(Match match);


    Match getMatch(Long id);

    void deleteMatch(Long id);

    List<Match> getAll();

    void deleteAll();

}
