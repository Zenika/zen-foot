package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Subclass;

/**
 * Created by raphael on 24/04/14.
 *
 * Represents the bet
 */
@Subclass
public class BetMatch extends AbsResultMatch implements Bet {


    public BetMatch(Match match){
        super();
        this.setMatch(match);
    }

    @Override
    public boolean isOnMatch() {
        return true;
    }

    @Override
    public boolean isOnCompetition() {
        return false;
    }
}
