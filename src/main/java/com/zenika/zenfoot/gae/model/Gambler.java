package com.zenika.zenfoot.gae.model;

import com.google.apphosting.api.ApiProxy;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import javax.persistence.Embedded;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by raphael on 28/04/14.
 */
@Entity
public class Gambler implements IGambler {

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

    public void remove(Bet bet){
        this.bets.remove(bet);
    }

    public List<Bet> getBets() {
        return this.bets;
    }




    /**
     * Return the bet with matchId, null if doesn't exist
     *
     * @param matchId
     * @return the bet with matchId, null if doesn't exist
     */
    private Bet getBet(Long matchId) {
        Bet toRet = null;
        for (Bet bet : bets) {
            if (bet.getMatchId().equals(matchId)) {
                toRet = bet;
                break;
            }
        }
        return toRet;
    }


    @Override

    public boolean equals(Object obj) {
        if (!(obj instanceof Gambler)) return false;
        return ((Gambler) obj).getEmail().equals(this.getEmail());
    }

}
