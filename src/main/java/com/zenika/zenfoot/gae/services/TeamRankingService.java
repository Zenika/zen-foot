package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.dao.TeamRankingDAO;
import com.zenika.zenfoot.gae.model.TeamRanking;

import java.util.List;

/**
 * Created by raphael on 26/08/14.
 */
public class TeamRankingService extends AbstractGenericService<TeamRanking> {

    public TeamRankingService(TeamRankingDAO teamRankingDAO) {
        super(teamRankingDAO);
    }

    public void reinitializePoints() {
       List<TeamRanking> teamRankings = this.getAll();
       for(TeamRanking teamRanking : teamRankings){
           teamRanking.setPoints(0);
           this.createOrUpdate(teamRanking);
       }
    }
    
    public TeamRanking getOrCreate(Long id) {
        return ((TeamRankingDAO)this.getDao()).getOrCreate(id);
    }
}
