package com.zenika.zenfoot.dao;

import com.zenika.zenfoot.model.Game;
import java.util.List;

public interface GameDao extends BaseDao<Game> {
    public List<Game> findIncoming();

    public List<Game> findPast();

    public List<Game> findRunning();
}
