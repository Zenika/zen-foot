package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Gambler {

    @Id
    private Long id;

    /**
     * The list of bets of the gambler
     */
    private List<Bet> bets = new ArrayList<Bet>();

    /**
     * The id of the user this gambler instance is attached to
     */
    @Index
    private String email;

    private String nom;

    private String prenom;

//    protected Set<Demande> demandes = new HashSet<>();

//    @Index
//    protected Set<StatutTeam> statutTeams =new HashSet<>();


    public Gambler(String email) {
        this.email = email;
    }

    public Gambler() {
    }
//
//    public void addTeams(Set<StatutTeam> teams){
//        this.statutTeams.addAll(teams);
//    }
//
//    public Set<StatutTeam> getStatutTeams() {
//        return statutTeams;
//    }
//
//    public void setStatutTeams(Set<StatutTeam> statutTeams) {
//        this.statutTeams = statutTeams;
//    }
//
//    public Set<Demande> getDemandes() {
//        return demandes;
//    }
//
//    public void setDemandes(Set<Demande> demandes) {
//        this.demandes = demandes;
//    }

    public Long getId() {
        return id;
    }

    public Gambler setId(Long id) {
        this.id = id;
        return this;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
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


//    @Override
//    public boolean equals(Object obj) {
//        if (!(obj instanceof Gambler)) return false;
//        return ((Gambler) obj).getEmail().equals(this.getEmail());
//    }

}
