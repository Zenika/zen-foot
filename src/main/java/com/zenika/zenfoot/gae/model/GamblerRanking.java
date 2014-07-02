package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class GamblerRanking {

    @Id
    private  Long id;

    @Index
    private Long gamblerId;

    protected String nom;

    protected String prenom;

    @Index
    protected int points;

    public GamblerRanking() {
    }

    public GamblerRanking(Long gamblerId, String nom, String prenom) {
        this.gamblerId = gamblerId;
        this.nom = nom;
        this.prenom = prenom;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGamblerId() {
        return gamblerId;
    }

    public void setGamblerId(Long gamblerId) {
        this.gamblerId = gamblerId;
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

    public void setPoints(int points) {
        this.points = points;
    }


    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void removePoints(int points) {
        this.points -= points;
    }

}
