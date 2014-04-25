package com.zenika.zenfoot.gae.model;

/**
 * Created by raphael on 24/04/14.
 */
public interface Bet {


    /**
     * Says whether or not the bet is on a match
     * @return true if the bet is on a match
     */
    boolean isOnMatch();

    /**
     * Says whether the bet is on the competition or not.
     * @return true if the bet is on the competition
     */
    boolean isOnCompetition();

}
