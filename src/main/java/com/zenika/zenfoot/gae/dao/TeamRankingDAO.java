package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.model.TeamRanking;

import java.util.List;

/**
 * Created by raphael on 25/08/14.
 */
public class TeamRankingDAO {

    public List<TeamRanking> getAll() {
        return OfyService.ofy().load().type(TeamRanking.class).order("-points").list();
    }

    public Key<TeamRanking> createUpdate(TeamRanking teamRanking) {
        Key<TeamRanking> key = OfyService.ofy().save().entity(teamRanking).now();
        return key;
    }

    public TeamRanking get(Long id) {
        return OfyService.ofy().load().type(TeamRanking.class).id(id).now();
    }

    /**
     * Retrieves the TeamRanking object from the DB corresponding to the Team with teamId id. If it doesn't exist, creates
     * the TeamRanking object and registers it
     * @param teamId
     * @return
     */
    public TeamRanking getOrCreate(Long teamId) {
        List<TeamRanking> teamRankings = OfyService.ofy().load().type(TeamRanking.class).filter("teamId", teamId).list();
        TeamRanking teamRanking = null;

        if (teamRankings.size() > 1) {
            throw new RuntimeException("There are more than one TeamRanking for this team : " + teamId);
        }

        if (teamRankings.size() > 0) {
            teamRanking = teamRankings.get(0);
        }
        else {
            teamRanking = new TeamRanking().setTeamId(teamId);
            this.createUpdate(teamRanking);
        }

        return teamRanking;
    }
}