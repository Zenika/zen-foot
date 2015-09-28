package com.zenika.zenfoot.gae.rest;

import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Ligue;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.LigueService;
import com.zenika.zenfoot.gae.services.SessionInfo;
import com.zenika.zenfoot.gae.services.TeamService;
import restx.WebException;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
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

    private final LigueService ligueService;

    private GamblerService gamblerService;

    private SessionInfo sessionInfo;

    private TeamService teamService;

    public LigueResource(@Named("gamblerService") GamblerService gamblerService, @Named("sessioninfo")SessionInfo sessionInfo, 
            TeamService teamService, LigueService ligueService) {
        this.gamblerService = gamblerService;
        this.sessionInfo = sessionInfo;
        this.teamService = teamService;
        this.ligueService = ligueService;

    }

    @POST("/joiner")
    @RolesAllowed(Roles.GAMBLER)
    public Gambler postJoiner(Gambler gambler) {
        return gamblerService.getFromKey(
                gamblerService.createOrUpdate(gambler));
    }


    @GET("/wannajoin")
    @RolesAllowed(Roles.GAMBLER)
    public Set<Gambler> wantToJoin() {
        /*Gambler gambler = gamblerService.get(sessionInfo.getUser());

        return gamblerService.wantToJoin(gambler);*/
        return null;
    }

    @GET("/teams")
    @PermitAll
    public List<Ligue> getTeams() {
        return teamService.getAll();
    }

    @GET("/wannajoinTeam/{id}")
    @RolesAllowed(Roles.GAMBLER)
    public Set<Gambler> wannaJoin(Long id){
        return gamblerService.wantToJoin(id);
    }


    @GET("/teams/{id}")
    @PermitAll
    public Ligue getTeam(Long id) {
        Ligue team = teamService.getFromID(id);
        if (team == null) {
            throw new WebException(HttpStatus.NOT_FOUND);
        } else {
            return team;
        }
    }
}
