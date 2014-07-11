package com.zenika.zenfoot.gae.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

import com.google.common.base.Optional;
import com.zenika.zenfoot.gae.model.*;
import com.zenika.zenfoot.gae.services.*;
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
    private LigueService ligueService;

    public BetResource(MatchService matchService,
                       @Named("sessioninfo") SessionInfo sessionInfo,
                       @Named("userService") UserService userService,
                       GamblerService gamblerService,
                       TeamDAO teamDAO,
                       RankingDAO rankingDAO,
                       LigueService ligueService) {

        this.sessionInfo = sessionInfo;
        this.matchService = matchService;
        this.userService = (MockUserService) userService;
        this.gamblerService = gamblerService;
        this.teamDAO = teamDAO;
        this.rankingDAO = rankingDAO;
        this.ligueService = ligueService;
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
        gamblerService.setScore(match);
        ligueService.recalculateScores();
    }

    @GET("/matchs")
    @PermitAll
    public List<Match> getMatchs() {
        return matchService.getMatchs();
    }

    @GET("/pays")
    @RolesAllowed(Roles.ADMIN)
    public List<String> getPays() {
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
    public void postMatch(Match match) {
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

    @POST("/gamblerAndTeam")
    @RolesAllowed(Roles.GAMBLER)
    public Gambler updateGambler(GamblerAndTeams gamblerAndTeams) {
        Key<Gambler> gamblerKey = gamblerService.addTeams(gamblerAndTeams.getTeams(), gamblerAndTeams.getGambler());
        return gamblerService.getGambler(gamblerKey);
    }

    /**
     * This is called when a gambler applied to your team. The owner of the team sends a GamblerStatutTeam object with the
     * accepted boolean set to true or false
     * @param gamblerStatutTeam
     */
    @PUT("/gamblersAndTeam")
    @RolesAllowed(Roles.GAMBLER)
    public void updateGambler2(GamblerStatutTeam gamblerStatutTeam){

        // The gambler who made the request :
        Gambler requestGambler = gamblerService.get(sessionInfo.getUser());
        //The gambler whose teams will be updated :
        Gambler receivedGambler = gamblerStatutTeam.getGambler();
        // The team to be updated :
        StatutTeam statutTeam = gamblerStatutTeam.getStatutTeam();

        Optional<Team> teamOpt = teamDAO.get(statutTeam.getTeam().getName());
        if(!teamOpt.isPresent() || !teamOpt.get().getOwnerEmail().equals(requestGambler.getEmail())){
            throw new WebException(HttpStatus.BAD_REQUEST);
        }

        Gambler gambler = gamblerService.get(receivedGambler.getId());
        if (gambler == null) throw new WebException(HttpStatus.BAD_REQUEST);

        StatutTeam existingST = gambler.getStatutTeam(statutTeam.getTeam().getId());

        //Trying to accept someone or remove someone from a statutTeam he made a request for, while the gambler never applied to it
        if (existingST == null || (statutTeam.isAccepted() && existingST.isAccepted())) {
            throw new WebException(HttpStatus.BAD_REQUEST);
        }

        // We'll now remove the statutTeam object.
        gambler.removeTeam(statutTeam.getTeam());

        if (statutTeam.isAccepted()) {
            gambler.addTeam(statutTeam);
            ligueService.recalcultateScore(statutTeam.getTeam(), gambler, true);
        }
        else{
            if(existingST.isAccepted()){
                ligueService.recalcultateScore(statutTeam.getTeam(),gambler,false);
            }
        }
        gamblerService.updateGambler(gambler);
    }

    @GET("/quitTeam/{teamId}")
    @RolesAllowed(Roles.GAMBLER)
    public Gambler quitTeam(Long teamId){
        Gambler gambler = gamblerService.get(sessionInfo.getUser());

        StatutTeam statutTeam = gambler.getStatutTeam(teamId);

        if(statutTeam ==null) throw new WebException(HttpStatus.NOT_FOUND);

        gambler.removeTeam(statutTeam.getTeam());

        if(statutTeam.isAccepted()){
            ligueService.recalcultateScore(statutTeam.getTeam(),gambler,false);
        }

        gamblerService.updateGambler(gambler);

        return gambler;
    }


    @PUT("/gambler")
    @RolesAllowed(Roles.GAMBLER)
    public List<Object> updateGambler2(Gambler newGambler) {

        User user = sessionInfo.getUser();
        Gambler gambler = gamblerService.get(user);
        List<Object> userGambler = new ArrayList<>();

        //Updating the gambler when the name changed
        if (!newGambler.getNom().equals(gambler.getNom()) || !newGambler.getPrenom().equals(gambler.getPrenom())) {
            GamblerRanking gamblerRanking = rankingDAO.findByGambler(gambler.getId());
            String prenom = newGambler.getPrenom();
            String nom = newGambler.getNom();

            user.setPrenom(prenom);
            user.setName(nom);
            gambler.setPrenom(prenom);
            gambler.setNom(nom);
            gamblerRanking.setNom(nom);
            gamblerRanking.setPrenom(prenom);
            Key<User> userKey = userService.createUser(user);
            User userRet = userService.get(userKey);
            Gambler gamblerRet = gamblerService.updateGambler(gambler);
            rankingDAO.createUpdate(gamblerRanking);

            userGambler.add(userRet);
            userGambler.add(gamblerRet);
        }
        // Simply updating with new value
        else {
            gambler.setStatutTeams(newGambler.getStatutTeams());
            Gambler gamblerRetrieved = gamblerService.updateGambler(gambler);
            userGambler.add(user);
            userGambler.add(gamblerRetrieved);
        }
        return userGambler;
    }

    @GET("/gambler")
    //@JsonView(Views.GamblerView.class)
    @RolesAllowed(Roles.GAMBLER)
    public Gambler getGambler() {
        User user = sessionInfo.getUser();
        return gamblerService.get(user);
    }

    @GET("/gamblers/{id}")
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
//        throw new UnsupportedOperationException();
        Set<Gambler> toRet = new HashSet<>();
        List<Gambler> gamblers = gamblerService.getAll();
        for (Gambler gambler : gamblers) {
            for (StatutTeam statutTeam : gambler.getStatutTeams()) {
                if (statutTeam.getTeam().getName().equals(team) && statutTeam.isAccepted()) {
                    toRet.add(gambler);
                    break;
                }
            }
        }
        return toRet;
    }

    @POST("/joiner")
    @RolesAllowed(Roles.GAMBLER)
    public Gambler postJoiner(Gambler gambler) {
        return gamblerService.updateGambler(gambler);
    }

    @POST("/invite")
    @RolesAllowed(Roles.GAMBLER)
    public void invite(GamblerStatutTeam gamblerStatutTeam){
        //The gambler who called that request
        Gambler requestCaller = gamblerService.get(sessionInfo.getUser());
        //The gambler that's to be invited
        Gambler sentGambler = gamblerStatutTeam.getGambler();
        //The statutTeam to add to sentGambler :
        StatutTeam statutTeam = gamblerStatutTeam.getStatutTeam();

        //The owner of the team is the only one allowed to invite people
        if(!requestCaller.getEmail().equals(statutTeam.getTeam().getOwnerEmail())){
            throw new WebException(HttpStatus.BAD_REQUEST);
        }

        Gambler regGambler = gamblerService.get(sentGambler.getId());
        if(regGambler.hasTeam(statutTeam.getTeam())) return;

        statutTeam.setInvitation(true);
        regGambler.addTeam(statutTeam);
        gamblerService.updateGambler(regGambler);
    }

    @PUT("/joinLigue")
    @RolesAllowed(Roles.GAMBLER)
    public void joinLigue(GamblerStatutTeam gamblerStatutTeam){
        //The gambler who called that request
        Gambler requestCaller = gamblerService.get(sessionInfo.getUser());
        //The gambler that's to be invited
        Gambler sentGambler = gamblerStatutTeam.getGambler();

        if(!requestCaller.getEmail().equals(sentGambler.getEmail())){
            throw new WebException(HttpStatus.BAD_REQUEST);
        }

        StatutTeam statutTeam = requestCaller.getStatutTeam(gamblerStatutTeam.getStatutTeam().getTeam().getId());

        if(statutTeam == null || ! statutTeam.isInvitation()){
            throw new WebException(HttpStatus.BAD_REQUEST);
        }

        statutTeam.setAccepted(true);

        gamblerService.updateGambler(requestCaller);

        ligueService.recalcultateScore(statutTeam.getTeam(),requestCaller,true);
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

    @GET("/wannajoinTeam/{id}")
    @RolesAllowed(Roles.GAMBLER)
    public Set<Gambler> wannaJoin(Long id){
        return gamblerService.wantToJoin(id);
    }


    @GET("/teams")
    @PermitAll
    public List<Team> getTeams() {
        return teamDAO.getAll();
    }

    @GET("/teams/{id}")
    @PermitAll
    public Team getTeam(Long id) {
        Team team = teamDAO.get(id);
        if (team == null) {
            throw new WebException(HttpStatus.NOT_FOUND);
        } else {
            return team;
        }
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

    @POST("/changePW")
    @RolesAllowed(Roles.GAMBLER)
    public void changePW(List<String> pwds) {
        String oldPW = pwds.get(0);
        String newPW = pwds.get(1);
        userService.resetPWD(sessionInfo.getUser().getEmail(), oldPW, newPW);
    }
}
