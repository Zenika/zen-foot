package com.zenika.zenfoot.service;

import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.Player;

public interface DataService {

    public void updateUserPoints();

    public void updateUserPoints(Player user);

    public Bet saveBet(Player player, Match match, int goalsForTeam1, int goalsForTeam2);

    public Match saveMatch(Match match, int goalsForTeam1, int goalsForTeam2);
}
