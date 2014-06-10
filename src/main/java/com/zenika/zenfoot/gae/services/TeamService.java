package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.model.Team;

import java.util.List;

/**
 * Created by raphael on 03/06/14.
 */
public class TeamService {

    protected TeamDAO teamDAO;

    public TeamService(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    public List<Team> getAll() {
        return teamDAO.getAll();
    }
}
