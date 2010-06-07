package com.zenika.zenfoot.dao;

import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Game;
import com.zenika.zenfoot.model.User;
import java.util.List;

public interface BetDao extends BaseDao<Bet> {
    public Bet createOrUpdate(User currentUser, Game match, int goalsForTeam1, int goalsForTeam2);

    public Bet find(User user, Game match);

    public List<Bet> findAll(Game match);

    public List<Bet> find(User user);
}
