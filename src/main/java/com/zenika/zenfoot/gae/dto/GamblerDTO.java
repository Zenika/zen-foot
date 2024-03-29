package com.zenika.zenfoot.gae.dto;

import com.zenika.zenfoot.gae.model.*;
import com.googlecode.objectify.Key;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GamblerDTO {

    private Long id;

    /**
     * The list of bets of the gambler
     */
    private List<Bet> bets = new ArrayList<Bet>();

    /**
     * The id of the user this gambler instance is attached to
     */
    private String email;

    private String nom;

    private String prenom;

    protected Set<Demande> demandes = new HashSet<>();

    protected Set<StatutTeam> statutTeams =new HashSet<>();
    
    private Event event;


    public GamblerDTO(String email) {
        this.email = email;
    }

    public GamblerDTO() {
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

    public GamblerDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public void addBet(Bet bet) {
        this.bets.add(bet);
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

    //    /**
//     * Return the bet with matchId, null if doesn't exist
//     *
//     * @param matchId
//     * @return the bet with matchId, null if doesn't exist
//     */
//    private Bet getBet(Long matchId) {
//        Bet toRet = null;
//        for (Bet bet : bets) {
//            if (bet.getMatchId().equals(matchId)) {
//                toRet = bet;
//                break;
//            }
//        }
//        return toRet;
//    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GamblerDTO)) return false;
        return ((GamblerDTO) obj).getEmail().equals(this.getEmail());
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
