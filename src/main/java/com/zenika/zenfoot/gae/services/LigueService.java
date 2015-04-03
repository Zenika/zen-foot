package com.zenika.zenfoot.gae.services;

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

    final private TeamService teamService;
    final private GamblerService gamblerService;
    final private Logger logger = Logger.getLogger(getClass().getName());
    final private TeamRankingService teamRankingService;

    public LigueService(TeamService teamService, GamblerService gamblerService, TeamRankingService teamRankingService) {
        this.teamService = teamService;
        this.gamblerService = gamblerService;
        this.teamRankingService = teamRankingService;

    }

    /**
     * Recalculate scores for ligues
     */
    public void recalculateScores(){
        Map<Long,Team> teamMap = new HashMap<>();
        //number of members in each team
        Map<Long, Integer> teamMembers = new HashMap<>();
        List<Team> teams = teamService.getAll();
        Map<Long, Gambler> gamblerRankingMap = new HashMap<>();
        List<Gambler> gamblerRankings = gamblerService.getAll();
        Map<Long, TeamRanking> teamRankingMap = new HashMap<>();
        List<TeamRanking> teamRankings = teamRankingService.getAll();

        for(Team team:teams){
            teamMap.put(team.getId(), team);
            teamMembers.put(team.getId(),gamblerService.nbGamblersInTeam(team));
        }

        for(TeamRanking teamRanking : teamRankings){
            teamRankingMap.put(teamRanking.getTeamId(), teamRanking);
        }

        for(Gambler gamblerRanking : gamblerRankings){
            gamblerRankingMap.put(gamblerRanking.getId(),gamblerRanking);
        }



        List<Gambler> gamblers = gamblerService.getAll();

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
            teamRankingService.createOrUpdate(teamRanking);
        }
    }


    public void recalcultateScore(Team team, Gambler requestCaller, boolean add) {
        TeamRanking teamRanking = teamRankingService.getOrCreate(team.getId());
        Gambler gamblerRanking = gamblerService.getFromID(requestCaller.getId());
        double formerMean = teamRanking.getPoints();
        int nbMembers = gamblerService.nbGamblersInTeam(team);
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

        teamRankingService.createOrUpdate(teamRanking);
    }
}
