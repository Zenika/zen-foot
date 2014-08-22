package com.zenika.zenfoot.gae.rest;

import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Team;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.SessionInfo;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import restx.security.RolesAllowed;

import javax.inject.Named;
import java.util.List;
import java.util.Set;

/**
 * Created by raphael on 11/08/14.
 */
@RestxResource
@Component
public class LigueResource {

    private GamblerService gamblerService;

    private SessionInfo sessionInfo;

    private TeamDAO teamDAO;

    public LigueResource(GamblerService gamblerService, @Named("sessioninfo")SessionInfo sessionInfo, TeamDAO teamDAO) {
        this.gamblerService = gamblerService;
        this.sessionInfo = sessionInfo;
        this.teamDAO = teamDAO;
    }

    @GET("/gamblersTeam/{team}")
    @RolesAllowed(Roles.GAMBLER)
    public Set<Gambler> getGamblersTeam(String team) {
        throw new UnsupportedOperationException();
//        Set<Gambler> toRet = new HashSet<>();
//        List<Gambler> gamblers = gamblerService.getAll();
//        for(Gambler gambler:gamblers){
//            for(StatutTeam statutTeam:gambler.getStatutTeams()){
//                if(statutTeam.getTeam().getName().equals(team)&&statutTeam.isAccepted()){
//                    toRet.add(gambler);
//                    break;
//                }
//            }
//        }
//        return toRet;
    }

    @POST("/joiner")
    @RolesAllowed(Roles.GAMBLER)
    public Gambler postJoiner(Gambler gambler) {
        return gamblerService.updateGambler(gambler);
    }

//    private static String getUrlConfirmation() {
//        String urlConfirmation = getHostUrl() + "/#/confirmSubscription/";
//
//        return urlConfirmation;
//    }
//
//    private static String getHostUrl() {
//        String hostUrl = null;
//        String environment = System.getProperty("com.google.appengine.runtime.environment");
//
//        if ("Production".equals(environment)) {
//            String applicationId = System.getProperty("com.google.appengine.application.id");
//            String version = System.getProperty("com.google.appengine.application.version");
//            hostUrl = "http://zenfoot.fr";
//        } else {
//            hostUrl = "http://localhost:8080";
//        }
//
//        return hostUrl;
//    }

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
}
