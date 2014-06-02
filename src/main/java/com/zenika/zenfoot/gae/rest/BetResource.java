package com.zenika.zenfoot.gae.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.jackson.Views;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.services.BetService;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.MatchService;
import com.zenika.zenfoot.gae.services.SessionInfo;
import com.zenika.zenfoot.user.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.PUT;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import restx.security.RolesAllowed;
import restx.security.UserService;

import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.services.BetService;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.MatchService;
import com.zenika.zenfoot.gae.services.MockUserService;
import com.zenika.zenfoot.gae.services.SessionInfo;
import com.zenika.zenfoot.user.User;


@RestxResource
@Component
public class BetResource {


    private MatchService matchService;
    private SessionInfo sessionInfo;
    private BetService betService;
    private GamblerService gamblerService;
    private MockUserService userService;
    
    public BetResource(MatchService matchService,
                       @Named("sessioninfo") SessionInfo sessionInfo,
                       @Named("betservice") BetService betService,
                       @Named("userService") UserService userService,
                       GamblerService gamblerService) {
        this.sessionInfo = sessionInfo;
        this.matchService = matchService;
        this.betService = betService;
        this.userService = (MockUserService) userService;
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


    @PUT("/matchs/{id}")
    @RolesAllowed(Roles.ADMIN)
    public void updateMatch(String id, Match match) {
        boolean isRegistered = matchService.getMatch(Long.parseLong(id)).getOutcome().isUpdated();
        Logger logger = Logger.getLogger(BetResource.class.getName());
        logger.log(Level.WARNING,"entering update");
        if (!isRegistered) {
            match.getOutcome().setUpdated(true);
            matchService.createUpdate(match);
            logger.log(Level.WARNING,"skipping calculation of scores");
            gamblerService.calculateScores(match);

        }
    }

    @GET("/matchs")
    @PermitAll
    public List<Match> getMatchs() {
        List<Match> matchs = matchService.getMatchs();
        return matchs;
    }

    /*@POST("/matchs")
    @RolesAllowed(Roles.ADMIN)
    public void updateMatchs(List<Match> matchs){
        for(Match match:matchs){
            matchService.createUpdate(match);
        }
    }*/


    @GET("/matchbets")
    @RolesAllowed({Roles.GAMBLER})
    public List<MatchAndBet> getBets() {
        Logger logger = Logger.getLogger(BetResource.class.getName());
        User user = sessionInfo.getUser();
        logger.log(Level.ALL,"retrieving user "+user.getEmail());

        Gambler gambler = gamblerService.get(user);
        List<Match> matchs = matchService.getMatchs();




        gamblerService.updateBets(gambler);

        List<Bet> bets = gambler.getBets();
        List<MatchAndBet> matchAndBets = new ArrayList<>();

        logger.log(Level.WARNING, "after creating the gambler, there are " + bets.size() + " bets");
        for (Match match : matchs) {
            Bet bet = gamblerService.getBet(gambler, match);
            if (bet == null) {
                logger.log(Level.WARNING, "bet is null!");
                logger.log(Level.WARNING, "bet corresponds to match ");
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
    public void postBets(List<MatchAndBet> matchAndBets) {


        Gambler gambler = gamblerService.get(sessionInfo.getUser());
        List<Bet> newList = new ArrayList<>();

        System.out.println("--------------------------------");
        System.out.println("/bets " + matchAndBets.size() + " matchAndBet json objects received");

        for (MatchAndBet matchAndBet : matchAndBets) {
            if (matchAndBet == null) {
                System.out.println("------------------------------------------");
                System.out.println("WHILE POSTING BETS");
                System.out.println("NULL BET FOR MATCH " + matchAndBet.getMatch());
            }
            newList.add(matchAndBet.getBet());
        }
        Gambler gambler1 = gamblerService.updateBets(newList, gambler);
        System.out.println("/ bets After posting bets ");
        System.out.println("There are " + gambler.getBets().size());
    }

    @GET("/gambler")
    //@JsonView(Views.GamblerView.class)
    @RolesAllowed(Roles.GAMBLER)
    public Gambler getGambler() {
        User user = sessionInfo.getUser();
        Logger logger = Logger.getLogger(BetResource.class.getName());
        logger.log(Level.WARNING,user.getEmail());
        Gambler gambler = gamblerService.get(user);

        return gambler;
    }

    @GET("/gamblers")
    @RolesAllowed(Roles.GAMBLER)
    public List<Gambler> getGamblers(){
        return gamblerService.getAll();
    }
    
    @POST("/performSubscription")
    @PermitAll
    public Boolean subscribe(User subscriber){
		Logger logger = Logger.getLogger(BetResource.class.getName());
        
		logger.log(Level.INFO, subscriber.getPrenom() );
		logger.log(Level.INFO, subscriber.getNom() );
        logger.log(Level.INFO, subscriber.getEmail() );
        logger.log(Level.INFO, subscriber.getPasswordHash() );
        
        subscriber.setRoles(Arrays.asList(Roles.GAMBLER));
        userService.createUser(subscriber);
        return Boolean.TRUE;
    }
    
}
