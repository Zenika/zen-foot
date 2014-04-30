package com.zenika.zenfoot.gae.rest;

import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.services.BetService;
import com.zenika.zenfoot.gae.services.MatchService;
import com.zenika.zenfoot.gae.services.SessionInfo;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.RolesAllowed;

import javax.inject.Named;
import java.util.List;

@RestxResource
@Component
public class BetResource {


    private final MatchService matchService;

     private SessionInfo sessionInfo;
    private BetService betService;

    public BetResource(MatchService matchService
            , @Named("sessioninfo") SessionInfo sessionInfo,
                       @Named("betservice")BetService betService) {
             this.sessionInfo = sessionInfo;
        this.matchService = matchService;
        this.betService = betService;
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
    @RolesAllowed({Roles.GAMBLER,Roles.ADMIN})
    public List<Match> getBets() {
        return matchService.getMatchs();
    }

    @POST("/bet")
    @RolesAllowed(Roles.GAMBLER)
    public void bet(List<Bet> bets){
        Gambler gambler = sessionInfo.getGambler();
        betService.save(bets, gambler);
    }



}
