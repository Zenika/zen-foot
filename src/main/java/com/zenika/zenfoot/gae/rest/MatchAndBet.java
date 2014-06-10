package com.zenika.zenfoot.gae.rest;

import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Match;

/**
 * Created by raphael on 30/04/14.
 */
public class MatchAndBet {

    private Bet bet;

    private Match match;

    public MatchAndBet() {
    }

    public Bet getBet() {
        return bet;
    }

    public MatchAndBet setBet(Bet bet) {
        this.bet = bet;
        return this;
    }

    public Match getMatch() {
        return match;
    }

    public MatchAndBet setMatch(Match match) {
        this.match = match;
        return this;
    }
}
