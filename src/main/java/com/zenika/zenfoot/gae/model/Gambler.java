package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    protected Set<Demande> demandes = new HashSet<>();

    @Index
    protected Set<StatutTeam> statutTeams =new HashSet<>();
    
    @Parent private Key<Event> event;
    
    @Index
    protected int points;


    public Gambler(String email) {
        this.email = email;
    }

    public Gambler() {
    }

    public void addTeams(Set<StatutTeam> teams){
        this.statutTeams.addAll(teams);
    }

    public Set<StatutTeam> getStatutTeams() {
        return statutTeams;
    }

    public void setStatutTeams(Set<StatutTeam> statutTeams) {
        this.statutTeams = statutTeams;
    }

    public Set<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(Set<Demande> demandes) {
        this.demandes = demandes;
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

    public boolean hasTeam(Team team) {
        for(StatutTeam statutTeam : this.getStatutTeams()){
            if(statutTeam.getTeam().equals(team)){
                return true;
            }
        }
        return false;
    }

    public boolean removeTeam(Team team){
        for(StatutTeam statutTeam : this.getStatutTeams()){
            if(statutTeam.getTeam().equals(team)){
                return this.getStatutTeams().remove(statutTeam);
            }
        }
        return false;
    }

    public StatutTeam getStatutTeam(Long id) {
        for (StatutTeam statutTeam : this.getStatutTeams()) {
            if (statutTeam.getTeam().getId().equals(id)) {
                return statutTeam;
            }
        }
        return null;
    }


    public void addTeam(StatutTeam statutTeam) {
        this.statutTeams.add(statutTeam);
    }

    public boolean isOwner(StatutTeam statutTeam) {
        return this.getEmail().equals(statutTeam.getTeam().getOwnerEmail());
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
}
