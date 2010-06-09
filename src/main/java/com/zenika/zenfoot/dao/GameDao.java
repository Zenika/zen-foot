package com.zenika.zenfoot.dao;

import com.zenika.zenfoot.model.Match;
import java.util.List;

public interface GameDao extends BaseDao<Match> {
    public List<Match> findIncoming();

    public List<Match> findPast();

    public List<Match> findRunning();
}
