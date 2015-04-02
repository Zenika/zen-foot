package com.zenika.zenfoot.gae.dto;

import com.zenika.zenfoot.gae.model.*;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;

/**
 * This is an implementation of a match bet.
 * <p/>
 * Created by raphael on 28/04/14.
 */
public class BetDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long matchId;
    private Integer score1;
    private Integer score2;
    
    Gambler gambler;

    public BetDTO() {
    }

    public BetDTO(Long matchId) {
        this.matchId = matchId;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Integer getScore1() {
        return score1;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    @Override
    public String toString() {
        return " match " + matchId + ": " + score1 + " - " + score2;
    }


    public boolean wasMade() {
        return this.score1!=null && this.score2!=null;
    }

    public Gambler getGambler() {
        return gambler;
    }

    public void setGambler(Gambler gambler) {
        this.gambler = gambler;
    }
}
