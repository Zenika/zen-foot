package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Index;

import java.util.List;

/**
 * Created by raphael on 28/08/14.
 */
public class GamblerBets {

    @Index
    private Long gamblerId;

    private List<Bet> bets;

    public GamblerBets() {
    }

    public GamblerBets(Gambler gambler){
        this.gamblerId = gambler.getId();
        this.bets = gambler.getBets();
    }

    public Long getGamblerId() {
        return gamblerId;
    }

    public void setGamblerId(Long gamblerId) {
        this.gamblerId = gamblerId;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }
}


