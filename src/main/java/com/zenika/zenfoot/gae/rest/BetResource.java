package com.zenika.zenfoot.gae.rest;

import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.services.MatchService;
import com.zenika.zenfoot.gae.services.SessionInfo;
import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import restx.security.RolesAllowed;

import javax.inject.Named;
import java.util.List;

@RestxResource
@Component
public class BetResource {


    private final MatchService matchService;

     private SessionInfo sessionInfo;

    public BetResource(MatchService matchService
                       , @Named("sessioninfo") SessionInfo sessionInfo
    ) {
             this.sessionInfo = sessionInfo;
        this.matchService = matchService;
    }


    @GET("/hello")
    @RolesAllowed(Roles.ADMIN)
    public String getMessage1() {
        return "Hello !";
    }

    @GET("/coucou")
    @RolesAllowed(Roles.GAMBLER)
    public String getCoucou() {
        return "coucou";
    }


    @GET("/matchs")
    @PermitAll
    public List<Match> getBets() {
        return matchService.getMatchs();
    }

}
