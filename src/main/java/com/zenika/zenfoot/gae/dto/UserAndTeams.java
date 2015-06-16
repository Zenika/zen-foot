package com.zenika.zenfoot.gae.dto;

import com.zenika.zenfoot.gae.model.Ligue;
import com.zenika.zenfoot.gae.model.User;

import java.util.List;

/**
 * Created by raphael on 02/06/14.
 */
public class UserAndTeams {

    protected User user;

    protected List<Ligue> teams;

    public UserAndTeams() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Ligue> getTeams() {
        return teams;
    }

    public void setTeams(List<Ligue> teams) {
        this.teams = teams;
    }
}
