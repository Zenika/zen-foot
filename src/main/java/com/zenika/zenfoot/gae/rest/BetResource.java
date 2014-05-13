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
import restx.security.PermitAll;
import restx.security.RolesAllowed;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    @PermitAll
    public List<Match> getMatchs(){
        List<Match> matchs = matchService.getMatchs();
        return matchs;
    }


    @GET("/matchbets")
    @RolesAllowed({Roles.GAMBLER})
    public List<MatchAndBet> getBets() {
        Gambler gambler = gamblerService.get(sessionInfo.getUser());
        List<Match> matchs = matchService.getMatchs();

        Logger logger = Logger.getLogger(BetResource.class.getName());

        if(gambler==null){
            logger.log(Level.WARNING,"gambler is null when calling /matchs");
            gambler = gamblerService.createGambler(sessionInfo.getUser(), matchs);
        }

        gamblerService.updateBets(gambler);

        List<Bet> bets = gambler.getBets();
        List<MatchAndBet> matchAndBets = new ArrayList<>();

        logger.log(Level.WARNING,"after creating the gambler, there are "+bets.size()+" bets");
        for(Match match : matchs){
            Bet bet=gamblerService.getBet(gambler, match);
            if(bet==null) {
                logger.log(Level.WARNING,"bet is null!");
                logger.log(Level.WARNING,"bet corresponds to match ");
                System.out.println("--------------------------------");
                System.out.println("WHILE RETRIEVING ALL BETS");
                System.out.println("NULL BET FOR MATCH " + match);
            }

            matchAndBets.add(new MatchAndBet().setMatch(match).setBet(bet));
        }

        return matchAndBets;
    }

    @POST("/bets")
    @RolesAllowed(Roles.GAMBLER)
    public void postBets(List<MatchAndBet> matchAndBets){


        Gambler gambler = gamblerService.get(sessionInfo.getUser());
        List<Bet> newList =new ArrayList<>();

        System.out.println("--------------------------------");
        System.out.println("/bets "+matchAndBets.size()+" matchAndBet json objects received");

        for(MatchAndBet matchAndBet: matchAndBets){

            if(matchAndBet==null){
                System.out.println("------------------------------------------");
                System.out.println("WHILE POSTING BETS");
                System.out.println("NULL BET FOR MATCH "+matchAndBet.getMatch());
            }
            newList.add(matchAndBet.getBet());
        }

        Gambler gambler1=gamblerService.updateBets(newList,gambler);
        System.out.println("/ bets After posting bets ");
        System.out.println("There are "+gambler.getBets().size());
    }





}
