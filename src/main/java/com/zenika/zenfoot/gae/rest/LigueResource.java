package com.zenika.zenfoot.gae.rest;

import com.google.common.base.Optional;
import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.dao.TeamRankingDAO;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.StatutTeam;
import com.zenika.zenfoot.gae.model.Team;
import com.zenika.zenfoot.gae.model.TeamRanking;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.LigueService;
import com.zenika.zenfoot.gae.services.SessionInfo;
import restx.WebException;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.PUT;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.PermitAll;
import restx.security.RolesAllowed;

import javax.inject.Named;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by raphael on 11/08/14.
 */
@RestxResource
@Component
public class LigueResource {

    private final LigueService ligueService;

    private GamblerService gamblerService;

    private SessionInfo sessionInfo;

    private TeamDAO teamDAO;

    private TeamRankingDAO teamRankingDAO;

    public LigueResource(GamblerService gamblerService, @Named("sessioninfo")SessionInfo sessionInfo, TeamDAO teamDAO, LigueService ligueService, TeamRankingDAO teamRankingDAO) {
        this.gamblerService = gamblerService;
        this.sessionInfo = sessionInfo;
        this.teamDAO = teamDAO;
        this.ligueService = ligueService;
        this.teamRankingDAO = teamRankingDAO;

    }


    @GET("/gamblersTeam/{team}")
    @RolesAllowed(Roles.GAMBLER)
    public Set<Gambler> getGamblersTeam(String team) {
//        throw new UnsupportedOperationException();
        Set<Gambler> toRet = new HashSet<>();
        List<Gambler> gamblers = gamblerService.getAll();
        for (Gambler gambler : gamblers) {
            for (StatutTeam statutTeam : gambler.getStatutTeams()) {
                if (statutTeam.getTeam().getName().equals(team) && statutTeam.isAccepted()) {
                    toRet.add(gambler);
                    break;
                }
            }
        }
        return toRet;
    }

    @POST("/joiner")
    @RolesAllowed(Roles.GAMBLER)
    public Gambler postJoiner(Gambler gambler) {
        return gamblerService.updateGambler(gambler);
    }


    @GET("/wannajoin")
    @RolesAllowed(Roles.GAMBLER)
    public Set<Gambler> wantToJoin() {
        Gambler gambler = gamblerService.get(sessionInfo.getUser());

        return gamblerService.wantToJoin(gambler);
    }

    @GET("/teams")
    @PermitAll
    public List<Team> getTeams() {
        return teamDAO.getAll();
    }

    @POST("/gamblerAndTeam")
    @RolesAllowed(Roles.GAMBLER)
    public Gambler updateGambler(GamblerAndTeams gamblerAndTeams) {
        Key<Gambler> gamblerKey = gamblerService.addTeams(gamblerAndTeams.getTeams(), gamblerAndTeams.getGambler());
        return gamblerService.getGambler(gamblerKey);
    }

    /**
     * This is called when a gambler applied to your team. The owner of the team sends a GamblerStatutTeam object with the
     * accepted boolean set to true or false
     * @param gamblerStatutTeam
     */
    @PUT("/gamblersAndTeam")
    @RolesAllowed(Roles.GAMBLER)
    public void updateGambler2(GamblerStatutTeam gamblerStatutTeam){

        // The gambler who made the request :
        Gambler requestGambler = gamblerService.get(sessionInfo.getUser());
        //The gambler whose teams will be updated :
        Gambler receivedGambler = gamblerStatutTeam.getGambler();
        // The team to be updated :
        StatutTeam statutTeam = gamblerStatutTeam.getStatutTeam();

        Optional<Team> teamOpt = teamDAO.get(statutTeam.getTeam().getName());
        if(!teamOpt.isPresent() || !teamOpt.get().getOwnerEmail().equals(requestGambler.getEmail())){
            throw new WebException(HttpStatus.BAD_REQUEST);
        }

        Gambler gambler = gamblerService.get(receivedGambler.getId());
        if (gambler == null) throw new WebException(HttpStatus.BAD_REQUEST);

        StatutTeam existingST = gambler.getStatutTeam(statutTeam.getTeam().getId());

        //Trying to accept someone or remove someone from a statutTeam he made a request for, while the gambler never applied to it
        if (existingST == null || (statutTeam.isAccepted() && existingST.isAccepted())) {
            throw new WebException(HttpStatus.BAD_REQUEST);
        }

        // We'll now remove the statutTeam object.
        gambler.removeTeam(statutTeam.getTeam());

        if (statutTeam.isAccepted()) {
            gambler.addTeam(statutTeam);
            ligueService.recalcultateScore(statutTeam.getTeam(), gambler, true);
        }
        else{
            if(existingST.isAccepted()){
                ligueService.recalcultateScore(statutTeam.getTeam(),gambler,false);
            }
        }
        gamblerService.updateGambler(gambler);
    }

    @GET("/quitTeam/{teamId}")
    @RolesAllowed(Roles.GAMBLER)
    public Gambler quitTeam(Long teamId){
        Gambler gambler = gamblerService.get(sessionInfo.getUser());

        StatutTeam statutTeam = gambler.getStatutTeam(teamId);

        if(statutTeam ==null) throw new WebException(HttpStatus.NOT_FOUND);

        gambler.removeTeam(statutTeam.getTeam());

        if(statutTeam.isAccepted()){
            ligueService.recalcultateScore(statutTeam.getTeam(),gambler,false);
        }

        gamblerService.updateGambler(gambler);

        return gambler;
    }


    @POST("/invite")
    @RolesAllowed(Roles.GAMBLER)
    public void invite(GamblerStatutTeam gamblerStatutTeam){
        //The gambler who called that request
        Gambler requestCaller = gamblerService.get(sessionInfo.getUser());
        //The gambler that's to be invited
        Gambler sentGambler = gamblerStatutTeam.getGambler();
        //The statutTeam to add to sentGambler :
        StatutTeam statutTeam = gamblerStatutTeam.getStatutTeam();

        //The owner of the team is the only one allowed to invite people
        if(!requestCaller.getEmail().equals(statutTeam.getTeam().getOwnerEmail())){
            throw new WebException(HttpStatus.BAD_REQUEST);
        }

        Gambler regGambler = gamblerService.get(sentGambler.getId());
        if(regGambler.hasTeam(statutTeam.getTeam())) return;

        statutTeam.setInvitation(true);
        regGambler.addTeam(statutTeam);
        gamblerService.updateGambler(regGambler);
    }

    @PUT("/joinLigue")
    @RolesAllowed(Roles.GAMBLER)
    public void joinLigue(GamblerStatutTeam gamblerStatutTeam){
        //The gambler who called that request
        Gambler requestCaller = gamblerService.get(sessionInfo.getUser());
        //The gambler that's to be invited
        Gambler sentGambler = gamblerStatutTeam.getGambler();

        if(!requestCaller.getEmail().equals(sentGambler.getEmail())){
            throw new WebException(HttpStatus.BAD_REQUEST);
        }

        StatutTeam statutTeam = requestCaller.getStatutTeam(gamblerStatutTeam.getStatutTeam().getTeam().getId());

        if(statutTeam == null || ! statutTeam.isInvitation()){
            throw new WebException(HttpStatus.BAD_REQUEST);
        }

        statutTeam.setAccepted(true);

        gamblerService.updateGambler(requestCaller);

        ligueService.recalcultateScore(statutTeam.getTeam(),requestCaller,true);
    }


    @GET("/wannajoinTeam/{id}")
    @RolesAllowed(Roles.GAMBLER)
    public Set<Gambler> wannaJoin(Long id){
        return gamblerService.wantToJoin(id);
    }


    @GET("/teams/{id}")
    @PermitAll
    public Team getTeam(Long id) {
        Team team = teamDAO.get(id);
        if (team == null) {
            throw new WebException(HttpStatus.NOT_FOUND);
        } else {
            return team;
        }
    }

    @GET("/teamRanking")
    @RolesAllowed(Roles.GAMBLER)
    public List<TeamRanking> teamRankings(){
        return this.teamRankingDAO.getAll();
    }
}
