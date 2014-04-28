package com.zenika.zenfoot.gae.model;

/**
 * This is an implementation of a match bet.
 *
 * Created by raphael on 28/04/14.
 */
public class Bet extends  IssueMatchAbs implements IBet{


    /**
     * Reference to a match id
     */
    protected Long matchId;



    public Bet(Long matchId) {
        super();
        this.matchId=matchId;
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
}
