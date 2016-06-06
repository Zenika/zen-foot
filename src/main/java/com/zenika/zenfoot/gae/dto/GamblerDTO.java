package com.zenika.zenfoot.gae.dto;

import com.zenika.zenfoot.gae.model.*;

import java.util.ArrayList;
import java.util.List;

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

    private String lastName;

    private String firstName;
    
    private Event event;
    
    private int points;


    public GamblerDTO(String email) {
        this.email = email;
    }

    public GamblerDTO() {
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GamblerDTO)) return false;
        return ((GamblerDTO) obj).getEmail().equals(this.getEmail());
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
