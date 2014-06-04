package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Index;

/**
 * Created by raphael on 04/06/14.
 */
public class StatutTeam {

    protected boolean accepted=false;

    @Index
    protected Team team;

    public StatutTeam() {
    }

    public boolean isAccepted() {
        return accepted;
    }

    public StatutTeam setAccepted(boolean accepted) {
        this.accepted = accepted;
        return this;
    }

    public Team getTeam() {
        return team;
    }

    public StatutTeam setTeam(Team team) {
        this.team = team;
        return this;
    }
}
