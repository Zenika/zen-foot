package com.zenika.zenfoot.dao;

import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.Player;
import java.util.List;

public interface BetDao extends BaseDao<Bet> {

    public List<Bet> find(Match match);

    public List<Bet> find(Player user);

    public Bet find(Player user, Match match);

    public Bet findOrCreate(Player user, Match match);
}
