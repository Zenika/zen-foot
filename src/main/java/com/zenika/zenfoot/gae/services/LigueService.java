package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.dao.GamblerDAO;
import com.zenika.zenfoot.gae.dao.RankingDAO;
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.dao.TeamRankingDAO;
import com.zenika.zenfoot.gae.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by raphael on 10/07/14.
 */
public class LigueService {

    private TeamDAO teamDAO;
    private GamblerDAO gamblerDAO;
    private RankingDAO rankingDAO;
    private Logger logger = Logger.getLogger(getClass().getName());
    private TeamRankingDAO teamRankingDAO;

    public LigueService(TeamDAO teamDAO, GamblerDAO gamblerDAO, RankingDAO rankingDAO, TeamRankingDAO teamRankingDAO) {
        this.teamDAO = teamDAO;
        this.gamblerDAO = gamblerDAO;
        this.rankingDAO = rankingDAO;
        this.teamRankingDAO = teamRankingDAO;

    }

    /**
     * Recalculate scores for ligues
     */
    public void recalculateScores(){
        Map<Long,Team> teamMap = new HashMap<>();
        //number of members in each team
        Map<Long, Integer> teamMembers = new HashMap<>();
        List<Team> teams = teamDAO.getAll();
        Map<Long, GamblerRanking> gamblerRankingMap = new HashMap<>();
        List<GamblerRanking> gamblerRankings = rankingDAO.getAll();
        Map<Long, TeamRanking> teamRankingMap = new HashMap<>();
        List<TeamRanking> teamRankings = teamRankingDAO.getAll();

        for(Team team:teams){
            teamMap.put(team.getId(), team);
            teamMembers.put(team.getId(),gamblerDAO.nbGamblersInTeam(team));
        }

        for(TeamRanking teamRanking : teamRankings){
            teamRankingMap.put(teamRanking.getTeamId(), teamRanking);
        }

        for(GamblerRanking gamblerRanking : gamblerRankings){
            gamblerRankingMap.put(gamblerRanking.getGamblerId(),gamblerRanking);
        }



        List<Gambler> gamblers = gamblerDAO.getAll();

        int denom=0;
        for(Gambler gambler:gamblers){
            for(StatutTeam statutTeam: gambler.getStatutTeams()){
                if(statutTeam.isAccepted()){
                    int nbGamblersInTeam = teamMembers.get(statutTeam.getTeam().getId());
                    TeamRanking teamRanking = teamRankingMap.get(statutTeam.getTeam().getId());
                    if(teamRanking == null){
                        teamRanking = new TeamRanking().setTeamId(statutTeam.getTeam().getId());
                    }
                    teamRanking.addPoints((double)gamblerRankingMap.get(gambler.getId()).getPoints()/(double)nbGamblersInTeam);
                }
            }
        }

        for(TeamRanking teamRanking:teamRankings){
            teamRankingDAO.createUpdate(teamRanking);
        }
    }


    public void recalcultateScore(Team team, Gambler requestCaller, boolean add) {
        TeamRanking teamRanking = teamRankingDAO.getOrCreate(team.getId());

        GamblerRanking gamblerRanking = rankingDAO.findByGambler(requestCaller.getId());
        double formerMean = teamRanking.getPoints();
        int nbMembers = gamblerDAO.nbGamblersInTeam(team);
        int gamblerPoints = gamblerRanking.getPoints();

        int formerPoints;
        int coef=1;
        if(add){
            //The cumulated number of points before adding the member :
            formerPoints = (int)(formerMean * (nbMembers -1));
        }
        else{
            coef=-1;
            formerPoints = (int) (formerMean * (nbMembers + 1));
        }
        double newMean = (double)(formerPoints + coef*gamblerPoints)/nbMembers;
        logger.log(Level.INFO,""+newMean+" ("+formerPoints+'/'+nbMembers+" participants)");

        teamRanking.setPoints(newMean);

        teamRankingDAO.createUpdate(teamRanking);
    }
}
