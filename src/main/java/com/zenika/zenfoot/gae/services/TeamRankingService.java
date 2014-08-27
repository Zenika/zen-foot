package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.dao.TeamRankingDAO;
import com.zenika.zenfoot.gae.model.TeamRanking;

import java.util.List;

/**
 * Created by raphael on 26/08/14.
 */
public class TeamRankingService {

    private TeamRankingDAO teamRankingDAO;

    public TeamRankingService(TeamRankingDAO teamRankingDAO) {
        this.teamRankingDAO = teamRankingDAO;
    }

    public List<TeamRanking> getAll() {
        return teamRankingDAO.getAll();
    }

    public void reinitializePoints() {
       List<TeamRanking> teamRankings = teamRankingDAO.getAll();
       for(TeamRanking teamRanking : teamRankings){
           teamRanking.setPoints(0);
           teamRankingDAO.createUpdate(teamRanking);
       }
    }
}
