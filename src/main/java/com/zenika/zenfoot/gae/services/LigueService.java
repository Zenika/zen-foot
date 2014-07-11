package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.dao.GamblerDAO;
import com.zenika.zenfoot.gae.dao.RankingDAO;
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.GamblerRanking;
import com.zenika.zenfoot.gae.model.StatutTeam;
import com.zenika.zenfoot.gae.model.Team;

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

    public LigueService(TeamDAO teamDAO, GamblerDAO gamblerDAO, RankingDAO rankingDAO) {
        this.teamDAO = teamDAO;
        this.gamblerDAO = gamblerDAO;
        this.rankingDAO = rankingDAO;
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

        for(Team team:teams){
            team.setPoints(0);
            teamMap.put(team.getId(), team);
            teamMembers.put(team.getId(),gamblerDAO.nbGamblersInTeam(team));
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
                    teamMap.get(statutTeam.getTeam().getId()).addPoints((double)gamblerRankingMap.get(gambler.getId()).getPoints()/(double)nbGamblersInTeam);
                }
            }
        }

        for(Team team:teams){
            teamDAO.createUpdate(team);
        }
    }


    public void recalcultateScore(Team team, Gambler requestCaller, boolean add) {
        Team regTeam = teamDAO.get(team.getId());
        GamblerRanking gamblerRanking = rankingDAO.findByGambler(requestCaller.getId());
        double formerMean = regTeam.getPoints();
        int nbMembers = gamblerDAO.nbGamblersInTeam(team);
        int gamblerPoints = gamblerRanking.getPoints();

        int formerPoints;
        if(add){
            //The cumulated number of points before adding the member :
            formerPoints = (int)(formerMean * (nbMembers -1));
        }
        else{
            formerPoints = (int) (formerMean * (nbMembers + 1));
        }
        double newMean = (double)(formerPoints + gamblerPoints)/nbMembers;
        logger.log(Level.INFO,""+newMean+" ("+formerPoints+'/'+nbMembers+" participants)");

        regTeam.setPoints(newMean);

        teamDAO.createUpdate(regTeam);
    }
}
