package com.zenika.zenfoot.gae.dto;

import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Team;

import java.util.List;

/**
 * Created by raphael on 06/06/14.
 */
public class GamblerAndTeams {

    protected Gambler gambler;

    protected List<Team> teams;

    public GamblerAndTeams() {
    }

    public Gambler getGambler() {
        return gambler;
    }

    public void setGambler(Gambler gambler) {
        this.gambler = gambler;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
