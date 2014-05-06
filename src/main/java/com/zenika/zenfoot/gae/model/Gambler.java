package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import javax.persistence.Embedded;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raphael on 28/04/14.
 */
@Entity
public class Gambler {

    @Id
    private Long id;

    /**
     * The list of bets of the gambler
     */
    protected List<Bet> bets = new ArrayList<Bet>();


    /**
     * The id of the user this gambler instance is attached to
     */
    @Index
    protected String email;

    public Gambler(String email) {
        this.email = email;
    }

    public Gambler() {
    }

    public Long getId() {
        return id;
    }

    public Gambler setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }


    public void addBet(Bet bet) {
        this.bets.add(bet);
    }

    public List<Bet> getBets() {
        return this.bets;
    }


    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Gambler)) return false;
        return ((Gambler) obj).getEmail().equals(this.getEmail());
    }

}
