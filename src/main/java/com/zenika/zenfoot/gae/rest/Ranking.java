package com.zenika.zenfoot.gae.rest;

/**
 * Created with IntelliJ IDEA.
 * User: raphael
 * Date: 21/05/14
 * Time: 11:54
 * To change this template use File | Settings | File Templates.
 */
public class Ranking {

    protected String email;

    protected String nom;

    protected String prenom;

    protected int points;

    public Ranking() {
    }

    public Ranking(String email, String nom, String prenom, int points) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.points = points;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
