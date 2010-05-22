package com.zenika.zenfoot.dao;

import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.User;

public interface BetDao extends BaseDao<Bet> {
    public Bet createOrUpdate(User currentUser, Match match, int goalsForTeam1, int goalsForTeam2);

    public Bet find(User user, Match match);
}
