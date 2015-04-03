package com.zenika.zenfoot.gae.dao;

import com.zenika.zenfoot.gae.IGenericDAO;
import com.zenika.zenfoot.gae.model.TeamRanking;


/**
 * Created by raphael on 25/08/14.
 */
public interface TeamRankingDAO extends IGenericDAO<TeamRanking> {

    /**
     * Retrieves the TeamRanking object from the DB corresponding to the Team with teamId id. If it doesn't exist, creates
     * the TeamRanking object and registers it
     * @param teamId
     * @return
     */
    TeamRanking getOrCreate(Long teamId);
}