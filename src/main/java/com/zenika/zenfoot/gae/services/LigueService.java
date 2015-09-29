package com.zenika.zenfoot.gae.services;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.dao.LigueDAO;
import com.zenika.zenfoot.gae.dto.GamblerDTO;
import com.zenika.zenfoot.gae.dto.LigueDTO;
import com.zenika.zenfoot.gae.mapper.MapperFacadeFactory;
import com.zenika.zenfoot.gae.model.*;
import com.zenika.zenfoot.gae.utils.KeyBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.logging.Logger;

/**
 * Created by raphael on 10/07/14.
 */
public class LigueService extends AbstractGenericService<Ligue> {

    final private TeamService teamService;
    final private GamblerService gamblerService;
    final private Logger logger = Logger.getLogger(getClass().getName());
    final private MapperFacadeFactory mapper;

    public LigueService(TeamService teamService, GamblerService gamblerService, LigueDAO ligueDAO,
                        MapperFacadeFactory mapper) {
        super(ligueDAO);
        this.teamService = teamService;
        this.gamblerService = gamblerService;
        this.mapper = mapper;
    }

    /**
     * Recalculate scores for ligues
     */
    public void recalculateScores() {
        /*Map<Long,Ligue> teamMap = new HashMap<>();
        //number of members in each team
        Map<Long, Integer> teamMembers = new HashMap<>();
        List<Ligue> teams = teamService.getAll();
        Map<Long, Gambler> gamblerRankingMap = new HashMap<>();
        List<Gambler> gamblerRankings = gamblerService.getAll();
        Map<Long, TeamRanking> teamRankingMap = new HashMap<>();
        List<TeamRanking> teamRankings = teamRankingService.getAll();

        for(Ligue team:teams){
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
        }*/
    }


    public void recalcultateScore(Ligue team, Gambler requestCaller, boolean add) {
        /*TeamRanking teamRanking = teamRankingService.getOrCreate(team.getId());
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

        teamRankingService.createOrUpdate(teamRanking);*/
    }

    public Ligue create(Event event, LigueDTO ligue, Gambler gambler) {
        ligue.setEvent(event);
        ligue.setOwner(mapper.getMapper().map(gambler, GamblerDTO.class));
        return this.createOrUpdateAndReturn(
                mapper.getMapper().map(ligue, Ligue.class));
    }

    public Ligue update(Event event, LigueDTO ligueToUpdate) {
        // ne modifier que les tableaux awaits et accepted
        Ligue ligue = this.getFromKey(
                KeyBuilder.buildLigueKey(ligueToUpdate.getId(), event.getId()));

        LigueDTO ligueDTO = mapper.getMapper().map(ligue, LigueDTO.class);
        ligueDTO.setAccepted(ligueToUpdate.getAccepted());
        ligueDTO.setAwaits(ligueToUpdate.getAwaits());
        ligueDTO.setEvent(event);


        return this.createOrUpdateAndReturn(
                mapper.getMapper().map(ligueDTO, Ligue.class));
    }

    public List<Ligue> getLiguesWithMembersFromEvent(Event event) {
        return ((LigueDAO) this.getDao()).getLiguesWithMembersFromEvent(event);
    }

    public void joinLigue(Ligue ligue, Gambler gambler, Event event) {
        LigueDTO ligueDTO = mapper.getMapper().map(ligue, LigueDTO.class);
        ligueDTO.initialize(gambler.getEmail());
        if (!ligueDTO.isIsOwner() && !ligueDTO.isIsAccepted() && !ligueDTO.isIsAwaits()) {
            ligue.getAwaits().add(KeyBuilder.buildGamblerRef(gambler.getId(), event.getId()));
            this.getDao().createUpdate(ligue);
        }
    }

    /**
     * Calculate score for ligue. Based on the mean of all ligue's members' score
     * @param ligue ligue
     * @return ligue's score
     */
    public double calculateScore(Ligue ligue) {
        double res = 0;
        List<Ref<Gambler>> members = ligue.getAccepted();
        if(!members.isEmpty()) {
            for(Ref<Gambler> g : members){
                res += g.get().getPoints();
                res = res / members.size();
                res = Math.round(res * 100) / 100;
            }
        }
        return res;
    }
}
