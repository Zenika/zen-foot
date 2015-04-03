package com.zenika.zenfoot.gae.dto;

import com.zenika.zenfoot.gae.model.Team;
import com.zenika.zenfoot.user.User;

import java.util.List;

/**
 * Created by raphael on 02/06/14.
 */
public class UserAndTeams {

    protected User user;

    protected List<Team> teams;

    public UserAndTeams() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
