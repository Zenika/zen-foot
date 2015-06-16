package com.zenika.zenfoot.gae.dto;

import com.zenika.zenfoot.gae.model.*;

public class LigueDTO {

    Long id;
    String name;
    GamblerDTO owner;
    boolean isOwner;
    boolean isAccepted;
    boolean isAwaits;
    Event event;
    GamblerDTO[] awaits;
    GamblerDTO[] accepted;

    public LigueDTO() {

    }

    public LigueDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LigueDTO setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isIsOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public boolean isIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public boolean isIsAwaits() {
        return isAwaits;
    }

    public void setIsAwaits(boolean isAwaits) {
        this.isAwaits = isAwaits;
    }

    public GamblerDTO getOwner() {
        return owner;
    }

    public void setOwner(GamblerDTO owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public GamblerDTO[] getAwaits() {
        return awaits;
    }

    public void setAwaits(GamblerDTO[] awaits) {
        this.awaits = awaits;
    }

    public GamblerDTO[] getAccepted() {
        return accepted;
    }

    public void setAccepted(GamblerDTO[] accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof LigueDTO)){
            return false;
        }
        return ((LigueDTO)obj).getName().equals(this.getName());
    }
    
    public void initializeIsAccepted(String email) {
        this.setIsAccepted(false);
        if (this.getAccepted() != null) {
            for (GamblerDTO gambler : this.getAccepted()) {
                if (gambler.getEmail().equals(email)) {
                    this.setIsAccepted(true);
                    return;
                }
            }
        }
    }
    
    public void initializeIsAwaits(String email) {
        this.setIsAwaits(false);
        if (this.getAwaits()!= null) {
            for (GamblerDTO gambler : this.getAwaits()) {
                if (gambler.getEmail().equals(email)) {
                    this.setIsAwaits(true);
                    return;
                }
            }
        }
    }
    public void initializeIsOwner(String email) {
        this.setIsOwner(this.getOwner().getEmail().equals(email));
    }

    public void initialize(String email) {
        this.initializeIsAccepted(email);
        this.initializeIsAwaits(email);
        this.initializeIsOwner(email);
    }
}
