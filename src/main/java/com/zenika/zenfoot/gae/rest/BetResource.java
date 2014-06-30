package com.zenika.zenfoot.gae.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Named;

import restx.RestxRequest;
import restx.RestxResponse;
import restx.WebException;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.PUT;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.PermitAll;
import restx.security.RolesAllowed;
import restx.security.UserService;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.dao.RankingDAO;
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.exception.JsonWrappedErrorWebException;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.GamblerRanking;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.model.Team;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.MatchService;
import com.zenika.zenfoot.gae.services.MockUserService;
import com.zenika.zenfoot.gae.services.SessionInfo;
import com.zenika.zenfoot.user.User;


@RestxResource
@Component
public class BetResource {

    private Logger logger = Logger.getLogger(getClass().getName());

    private MatchService matchService;
    private SessionInfo sessionInfo;
    private GamblerService gamblerService;
    private MockUserService userService;
    private TeamDAO teamDAO;
    private RankingDAO rankingDAO;

    public BetResource(MatchService matchService,
                       @Named("sessioninfo") SessionInfo sessionInfo,
                       @Named("userService") UserService userService,
                       GamblerService gamblerService,
                       TeamDAO teamDAO,
                       RankingDAO rankingDAO) {

        this.sessionInfo = sessionInfo;
        this.matchService = matchService;
        this.userService = (MockUserService) userService;
        this.gamblerService = gamblerService;
        this.teamDAO = teamDAO;
        this.rankingDAO = rankingDAO;
    }


    @POST("/redirectAfterLogin")
    public void redirectAfterLogin() {
        throw new WebException(HttpStatus.FOUND) {
            @Override
            public void writeTo(RestxRequest restxRequest, RestxResponse restxResponse) throws IOException {
                restxResponse
                        .setStatus(getStatus())
                        .setHeader("Location", "/#/bets");
            }
        };
    }

    @PUT("/matchs/{id}")
    @RolesAllowed(Roles.ADMIN)
    public void updateMatch(String id, Match match) {
        boolean isRegistered = matchService.getMatch(Long.parseLong(id)).isScoreUpdated();
        if (!isRegistered) {
            match.setScoreUpdated(true);
            matchService.createUpdate(match);
            gamblerService.calculateScores(match);
        }
        //TODO ELSE ?
    }

    @GET("/matchs")
    @PermitAll
    public List<Match> getMatchs() {
        return matchService.getMatchs();
    }

    @GET("/pays")
    @RolesAllowed(Roles.ADMIN)
    public List<String> getPays(){
        return matchService.getPays();
    }

    /*@POST("/matchs")
    @RolesAllowed(Roles.ADMIN)
    public void updateMatchs(List<Match> matchs){
        for(Match match:matchs){
            matchService.createUpdate(match);
        }
    }*/

    @POST("/matchs")
    @RolesAllowed(Roles.ADMIN)
    public void postMatch(Match match){
        matchService.createUpdate(match);
    }

    @GET("/bets")
    @RolesAllowed({Roles.GAMBLER})
    public List<Bet> getBets() {
        User user = sessionInfo.getUser();
        Gambler gambler = gamblerService.get(user);
        return gambler.getBets();
    }

    @POST("/bets")
    @RolesAllowed(Roles.GAMBLER)
    public void postBets(List<Bet> bets) {
        User user = sessionInfo.getUser();
        Gambler gambler = gamblerService.get(user);
        gamblerService.updateBets(bets, gambler);
    }

    @POST("/gambler")
    @RolesAllowed(Roles.GAMBLER)
    public Gambler updateGambler(GamblerAndTeams gamblerAndTeams) {
        Key<Gambler> gamblerKey = gamblerService.addTeams(gamblerAndTeams.getTeams(), gamblerAndTeams.getGambler());
        return gamblerService.getGambler(gamblerKey);
    }

    @GET("/gambler")
    //@JsonView(Views.GamblerView.class)
    @RolesAllowed(Roles.GAMBLER)
    public Gambler getGambler() {
        User user = sessionInfo.getUser();
        return gamblerService.get(user);
    }

    @GET("/gambler/{id}")
    @RolesAllowed({Roles.GAMBLER, Roles.ADMIN})
    public Gambler getGambler(Long id) {
        return gamblerService.get(id);
    }

    @GET("/gamblers")
    @RolesAllowed(Roles.GAMBLER)
    public List<Gambler> getGamblers() {
        return gamblerService.getAll();
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

    @POST("/performSubscription")
    @PermitAll
    public void subscribe(UserAndTeams subscriber) {
        String email = subscriber.getUser().getEmail();
        User alreadyExistingUser = userService.getUserByEmail(email);

        if (alreadyExistingUser != null) {
            throw new JsonWrappedErrorWebException("SUBSCRIPTION_ERROR_ALREADY_USED_EMAIL",
                    String.format("L'email %s est déjà pris par un autre utilisateur !", email));
        }

        subscriber.getUser().setPassword(subscriber.getUser().getPasswordHash());
        subscriber.getUser().setRoles(Arrays.asList(Roles.GAMBLER));
        subscriber.getUser().setIsActive(Boolean.TRUE);

//        TODO Send mail
//        String subject = "Confirmation d'inscription à Zen Foot";
//        String urlConfirmation = "<a href='" + getUrlConfirmation() + subscriber.getUser().getEmail() + "'> Confirmation d'inscription </a>";
//        String messageContent = "Mr, Mme " + subscriber.getUser().getNom() + " Merci de cliquer sur le lien ci-dessous pour confirmer votre inscription. \n\n" + urlConfirmation;
//        subscriber.getUser().setIsActive(Boolean.FALSE);

        Key<User> keyUser = userService.createUser(subscriber.getUser());
        User user = userService.get(keyUser);
        Key<Gambler> gamblerKey = gamblerService.createGambler(user, matchService.getMatchs());

        Gambler gambler = gamblerService.getGambler(gamblerKey);
        gamblerService.addTeams(subscriber.getTeams(), gambler);
    }

    @GET("/confirmSubscription")
    @PermitAll
    public String confirmSubscription(String email) {
        final User user = userService.getUserByEmail(email);

        if (user != null && user.getIsActive() != null && !user.getIsActive()) {
            user.setIsActive(Boolean.TRUE);
            userService.updateUser(user);
            return Boolean.TRUE.toString();
        }

        return Boolean.FALSE.toString();
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

    @GET("/rankings")
    @RolesAllowed(Roles.GAMBLER)
    public List<GamblerRanking> rankings() {
        return rankingDAO.getAll();
    }

    @GET("/ranking")
    @RolesAllowed(Roles.GAMBLER)
    public GamblerRanking ranking() {
        Gambler gambler = gamblerService.get(sessionInfo.getUser());
        return rankingDAO.findByGambler(gambler.getId());
    }
}
