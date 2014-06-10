package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@Entity
public class GamblerRanking {

    @Index
    private Long gamblerId;

    protected String nom;

    protected String prenom;

    protected int points;

    public GamblerRanking(Long gamblerId, String nom, String prenom) {
        this.gamblerId = gamblerId;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

}
