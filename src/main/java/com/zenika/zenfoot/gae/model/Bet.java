package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * This is an implementation of a match bet.
 * <p/>
 * Created by raphael on 28/04/14.
 */
public class Bet extends IssueMatchAbs implements IBet {


    /**
     * Reference to a match id
     */
    protected Long matchId;

    public Bet(){

    }

    public Bet(Long matchId) {
        super();
        this.matchId = matchId;
    }



    public Long getMatchId() {
        return matchId;
    }

    @Override
    public boolean isWon() {
        //TODO
        return false;
    }

    @Override
    public boolean hasHappened() {
        //TODO
        return false;
    }

    @Override
    public String toString() {
        return" "+score1.getScore()+" : "+score2.getScore();
    }
}
