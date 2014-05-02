package com.zenika.zenfoot.gae.rest;

import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.services.BetService;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.MatchService;
import com.zenika.zenfoot.gae.services.SessionInfo;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.RolesAllowed;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@RestxResource
@Component
public class BetResource {


    private final MatchService matchService;

    private SessionInfo sessionInfo;
    private BetService betService;
    private GamblerService gamblerService;

    public BetResource(MatchService matchService
            , @Named("sessioninfo") SessionInfo sessionInfo,
                       @Named("betservice") BetService betService,
                       GamblerService gamblerService) {
        this.sessionInfo = sessionInfo;
        this.matchService = matchService;
        this.betService = betService;
        this.gamblerService = gamblerService;
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
    @RolesAllowed({Roles.GAMBLER, Roles.ADMIN})
    public List<MatchAndBet> getBets() {
        Gambler gambler = gamblerService.get(sessionInfo.getUser());
        List<Match> matchs = matchService.getMatchs();

        if(gambler==null){
            gambler = gamblerService.createGambler(sessionInfo.getUser(),matchs);
        }

        List<Bet> bets = gambler.getBets();
        List<MatchAndBet> matchAndBets = new ArrayList<>();

        for(Match match : matchs){
            matchAndBets.add(new MatchAndBet().setMatch(match).setBet(gamblerService.getBet(gambler,match)));
        }

        return matchAndBets;
    }

    @POST("/bet")
    @RolesAllowed(Roles.GAMBLER)
    public void bet(List<Bet> bets) {
    }


}
