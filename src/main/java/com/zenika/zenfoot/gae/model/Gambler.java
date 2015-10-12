package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import java.util.List;


@Entity
public class Gambler {

    @Id
    private Long id;

    /**
     * The id of the user this gambler instance is attached to
     */
    @Index
    private String email;

    private String nom;

    private String prenom;
    
    @Parent private Key<Event> event;
    
    @Index
    protected int points;

    //ligues where the gambler is invited to join
    private List<Ref<Ligue>> liguePropositions;

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

    public void setEmail(String email) {
        this.email = email;
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
    
    public void addPoints(int points) {
        this.points += points;
    }
    
    public void removePoints(int points) {
        this.points -= points;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Gambler)) return false;
        return ((Gambler) obj).getEmail().equals(this.getEmail());
    }

    public Key<Event> getEvent() {
        return event;
    }

    public void setEvent(Key<Event> event) {
        this.event = event;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


    public List<Ref<Ligue>> getLiguePropositions() {
        return liguePropositions;
    }

    public void setLiguePropositions(List<Ref<Ligue>> liguePropositions) {
        this.liguePropositions = liguePropositions;
    }

}
