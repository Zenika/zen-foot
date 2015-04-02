package com.zenika.zenfoot.gae.rest;

import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.dto.MatchDTO;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.services.EventService;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.MatchService;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import restx.security.RolesAllowed;

import java.util.List;

/**
 * Created by raphael on 11/08/14.
 */
@RestxResource
@Component
public class MatchResource {

    private GamblerService gamblerService;

    private MatchService matchService;
    
    private EventService eventService;

    public MatchResource(GamblerService gamblerService, MatchService matchService, EventService eventService) {
        this.gamblerService = gamblerService;
        this.eventService = eventService;
        this.matchService = matchService;
    }

    @GET("/matchs")
    @PermitAll
    public List<Match> getMatchs() {
        return matchService.getAll();
    }

    @GET("/pays")
    @RolesAllowed(Roles.ADMIN)
    public List<String> getPays(){
        return matchService.getPays();
    }

    @POST("/matchs")
    @RolesAllowed(Roles.ADMIN)
    public void postMatch(MatchDTO match){
        matchService.createUpdate(match);
    }

}
