package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;


@Entity
public class Gambler {

    @Id
    private Long id;

    /**
     * The id of the user this gambler instance is attached to
     */
    @Index
    private String email;

    private String lastName;

    private String firstName;
    
    @Parent private Key<Event> event;
    
    @Index
    protected int points;


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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
}
