package com.zenika.zenfoot.gae.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raphael on 28/04/14.
 */
public class Gambler {

    /**
     * The list of bets of the gambler
     */
    protected List<IBet> bets;

    /**
     * The id of the user to which the gambler instance is attached
     */
    protected String email;

    public Gambler(String email) {
        this.email = email;
        this.bets = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void addBet(IBet bet) {
        this.bets.add(bet);
    }

    public List<IBet> getBets() {
        return this.bets;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Gambler)) return false;
        return ((Gambler) obj).getEmail().equals(this.getEmail());
    }

}
