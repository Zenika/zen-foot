package com.zenika.zenfoot.gae.model;

/**
 * Created by raphael on 24/04/14.
 */
public class BetCompetition implements Bet {
    @Override
    public boolean isOnMatch() {
        return false;
    }

    @Override
    public boolean isOnCompetition() {
        return true;
    }
}
