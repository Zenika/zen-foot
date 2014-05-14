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

    public Bet() {

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
    public int calculateScore(Outcome outcome) {
        //TODO implement this
        return 0;
    }


    @Override
    public boolean hasHappened() {
        //TODO
        return false;
    }

    /**
     * Used to know if a bet is exactly the same as another one. That includes checking the match the bet is related to is the same, and checking
     * that scores are equals.
     * @param bet the bet to compare
     * @return true if the bets are related to the same match and have same scores
     */
    public boolean exactSame(Bet bet){
        return (bet.getMatchId().equals(this.getMatchId()) && bet.getScore1().equals(this.getScore1()) && bet.getScore2().equals(this.getScore2()));

    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Bet)) return false;
        Bet betObj = (Bet) obj;
        return (betObj.getMatchId().equals(this.getMatchId()) && betObj.getScore1().equals(this.getScore1()) && betObj.getScore2().equals(this.getScore2()));
    }

    @Override
    public String toString() {
        return " " + score1.getScore() + " : " + score2.getScore();
    }



}
