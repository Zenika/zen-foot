package com.zenika.zenfoot.dao;

import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.Team;
import java.util.List;

public interface MatchDao extends BaseDao<Match> {
    public List<Match> findIncoming();

    public List<Match> findPast();

    public List<Match> findRunning();

    public int count(Team team);
}
