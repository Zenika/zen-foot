package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

@Entity
public class Team {

    @Id
    public Long id;

    @Index
    public String name;

    @Unindex
    protected String ownerEmail;

    @Index
    protected double points=0;

    public Team() {

    }

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Team setName(String name) {
        this.name = name;
        return this;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public void addPoints(double points) {
        this.points += points;
    }

    public void decPoints(int points){
        double newPoints = (this.points-points);
        this.points = (newPoints > 0)?newPoints:0;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Team)){
            return false;
        }
        return ((Team)obj).getName().equals(this.getName());
    }
}
