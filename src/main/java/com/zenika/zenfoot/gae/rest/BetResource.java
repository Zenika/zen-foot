package com.zenika.zenfoot.gae.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Optional;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.jackson.Views;
import com.zenika.zenfoot.gae.model.*;
import com.zenika.zenfoot.gae.services.BetService;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.MatchService;
import com.zenika.zenfoot.gae.services.SessionInfo;
import com.zenika.zenfoot.user.User;

import java.util.*;
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
import com.zenika.zenfoot.gae.services.MailSenderService;
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

    private TeamDAO teamDAO;

    private MailSenderService mailSenderService;
    
    public BetResource(MatchService matchService,
                       @Named("sessioninfo") SessionInfo sessionInfo,
                       @Named("betservice") BetService betService,
                       @Named("userService") UserService userService,
                       GamblerService gamblerService,
                       TeamDAO teamDAO) {

        this.sessionInfo = sessionInfo;
        this.matchService = matchService;
        this.betService = betService;
        this.userService = (MockUserService) userService;
        this.gamblerService = gamblerService;
        this.mailSenderService = new MailSenderService();
        this.teamDAO = teamDAO;
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
        logger.log(Level.ALL, "retrieving user " + user.getEmail());

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
        logger.log(Level.INFO, "current gambler is "+user.getEmail());

        Gambler gambler = gamblerService.get(user);
        logger.log(Level.INFO,"id of gambler : "+gambler.getId()+" ("+gambler.getNom()+")");

        return gambler;
    }

    @GET("/gambler/{email}")
    @PermitAll
    public Gambler getGambler(String email){
        Logger logger = Logger.getLogger(BetResource.class.getName());
        logger.log(Level.INFO,email);
        return gamblerService.getFromEmail(email);
    }

    @GET("/gamblers")
    @RolesAllowed(Roles.GAMBLER)
    public List<Gambler> getGamblers() {
        return gamblerService.getAll();
    }

    @POST("/joiner")
    @RolesAllowed(Roles.GAMBLER)
    public Gambler postJoiner(Gambler gambler){

        return gamblerService.updateGambler(gambler);
    }



    @POST("/performSubscription")
    @PermitAll
    public void subscribe(UserAndTeams subscriber) {
        Logger logger = Logger.getLogger(BetResource.class.getName());

        final String subject = "Confirmation d'inscription à Zen Foot";
        final String urlConfirmation = "<a href='" + getUrlConfirmation() + subscriber.getUser().getEmail() + "'> Confirmation d'inscription </a>";
        final String messageContent = "Mr, Mme " +subscriber.getUser().getNom()+ " Merci de cliquer sur le lien ci-dessous pour confirmer votre inscription. \n\n" + urlConfirmation;
        logger.log(Level.INFO,"---------------subscribe-------------");
        logger.log(Level.INFO,subscriber.getUser().getPasswordHash());
        subscriber.getUser().setRoles(Arrays.asList(Roles.GAMBLER));
        subscriber.getUser().setIsActive(Boolean.FALSE);

        Key<User> keyUser = userService.createUser(subscriber.getUser());
        User user = userService.get(keyUser);
        Key<Gambler> gamblerKey = gamblerService.createGambler(user, matchService.getMatchs());
        Gambler gambler = gamblerService.getGambler(gamblerKey);

        Set<StatutTeam> testSet = new HashSet<>();

        for (Team team : subscriber.getTeams()) {
            Optional<Team> optTeam = teamDAO.get(team.getName());

            Team toRegister = null;
            boolean owner = false;

            if (optTeam.isPresent()) { // Team has already been created
                toRegister = optTeam.get();
                logger.log(Level.INFO,"Id for team : "+toRegister.getId());
            } else { //The team was created by the user and thus, the latter is the owner of it
                logger.log(Level.INFO,"No team found");
                team.setOwnerEmail(gambler.getEmail());
                Key<Team> teamKey = teamDAO.createUpdate(team);
                toRegister = teamDAO.get(teamKey);
                owner = true;
            }
            testSet.add(new StatutTeam().setTeam(toRegister).setAccepted(owner));

        }


        gamblerService.updateTeams(testSet, gambler);
    }
    
    @GET("/confirmSubscription")
    @PermitAll
    public String confirmSubscription(String email) {
    	final User user = userService.getUserByEmail(email);
    	
    	if(user != null && user.getIsActive() != null && !user.getIsActive()) {
    		user.setIsActive(Boolean.TRUE);
    		userService.updateUser(user);
    		return Boolean.TRUE.toString();
    	}
    	
    	return Boolean.FALSE.toString();
    }
    
    private static String getUrlConfirmation() {
    	String urlConfirmation = getHostUrl() + "/#/confirmSubscription/";
    	
    	return urlConfirmation;
    }
    
    private static String getHostUrl() {
        String hostUrl = null;
        String environment = System.getProperty("com.google.appengine.runtime.environment");

        if ("Production".equals(environment)) {
            String applicationId = System.getProperty("com.google.appengine.application.id");
            String version = System.getProperty("com.google.appengine.application.version");
            // TODO Utiliser http://zenfoo.fr comme hostUrl.
            hostUrl = "http://" + version + "." + applicationId + ".appspot.com";
        } else {
            hostUrl = "http://localhost:8080";
        }

        return hostUrl;
    }

    @GET("/wannajoin")
    @RolesAllowed(Roles.GAMBLER)
    public Set<Gambler> wantToJoin(){
        Gambler gambler = gamblerService.get(sessionInfo.getUser());
        return gamblerService.wantToJoin(gambler);
    }

    @GET("/teams")
    @PermitAll
    public List<Team> getTeams() {

        return teamDAO.getAll();
    }



}
