package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

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
    //@JsonView(Views.GamblerView.class)
    protected List<Bet> bets = new ArrayList<Bet>();

    protected int points=0;

    /**
     * The id of the user this gambler instance is attached to
     */
    @Index
    protected String email;

    protected String nom;

    protected String prenom;

    private List<Team> teams = new ArrayList<>();

    public List<Team> getTeams() {
        return teams;
    }

    public Gambler setTeams(List<Team> teams) {
        this.teams = teams;
        return this;
    }

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


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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


    public int getPoints() {
        return points;
    }



    public void addPoints(int points){
        this.points+=points;
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
