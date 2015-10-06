package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Ligue {

    // marker class used to define a Load group
    public static class Members{}

    @Id
    public Long id;
    @Index
    public String name;

    @Load
    Ref<Gambler> owner;
    
    @Parent private Key<Event> event;
    
    List<Ref<Gambler>> awaits;
    
    @Load(Members.class)
    List<Ref<Gambler>> accepted;

    public Ligue() {
        awaits = new ArrayList<>();
        accepted = new ArrayList<>();
    }

    public Ligue(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Ligue setName(String name) {
        this.name = name;
        return this;
    }

    public Ref<Gambler> getOwner() {
        return owner;
    }

    public void setOwner(Ref<Gambler> owner) {
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

    public List<Ref<Gambler>> getAwaits() {
        return awaits;
    }

    public void setAwaits(List<Ref<Gambler>> awaits) {
        this.awaits = awaits;
    }

    public List<Ref<Gambler>> getAccepted() {
        return accepted;
    }

    public void setAccepted(List<Ref<Gambler>> accepted) {
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
