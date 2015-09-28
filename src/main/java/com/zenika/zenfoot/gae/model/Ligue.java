package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Ligue {

    @Id
    public Long id;
    @Index
    public String name;
    
    Key<Gambler> owner;
    
    @Parent private Key<Event> event;
    
    Key<Gambler>[] awaits;
    
    Key<Gambler>[] accepted;

    public Ligue() {

    }

    public Ligue(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Ligue setName(String name) {
        this.name = name;
        return this;
    }

    public Key<Gambler> getOwner() {
        return owner;
    }

    public void setOwner(Key<Gambler> owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Key<Event> getEvent() {
        return event;
    }

    public void setEvent(Key<Event> event) {
        this.event = event;
    }

    public Key<Gambler>[] getAwaits() {
        return awaits;
    }

    public void setAwaits(Key<Gambler>[] awaits) {
        this.awaits = awaits;
    }

    public Key<Gambler>[] getAccepted() {
        return accepted;
    }

    public void setAccepted(Key<Gambler>[] accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Ligue)){
            return false;
        }
        return ((Ligue)obj).getName().equals(this.getName());
    }
}
