package com.zenika.zenfoot.gae.rest;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.impl.Session;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.dao.RankingDAO;
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.model.*;
import com.zenika.zenfoot.gae.services.*;
import com.zenika.zenfoot.user.User;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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

import javax.inject.Named;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestxResource
@Component
public class BetResource {


    private MatchService matchService;
    private SessionInfo sessionInfo;
    private BetService betService;
    private GamblerService gamblerService;
    private MockUserService userService;

    private TeamDAO teamDAO;
    private RankingDAO rankingDAO;
    private MailSenderService mailSenderService;

    public BetResource(MatchService matchService,
                       @Named("sessioninfo") SessionInfo sessionInfo,
                       @Named("betservice") BetService betService,
                       @Named("userService") UserService userService,
                       GamblerService gamblerService,
                       TeamDAO teamDAO,
                       RankingDAO rankingDAO) {

        this.sessionInfo = sessionInfo;
        this.matchService = matchService;
        this.betService = betService;
        this.userService = (MockUserService) userService;
        this.gamblerService = gamblerService;
        this.mailSenderService = new MailSenderService();
        this.teamDAO = teamDAO;
        this.rankingDAO=rankingDAO;
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
        Logger logger = Logger.getLogger(BetResource.class.getName());
        logger.log(Level.WARNING, "entering update");
        if (!isRegistered) {
            match.setScoreUpdated(true);
            matchService.createUpdate(match);
            logger.log(Level.WARNING, "skipping calculation of scores");
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
        Gambler gambler = gamblerService.getGambler(gamblerKey);
        return gambler;
    }

    @GET("/gambler")
    //@JsonView(Views.GamblerView.class)
    @RolesAllowed(Roles.GAMBLER)
    public Gambler getGambler() {
        User user = sessionInfo.getUser();
        Gambler gambler = gamblerService.get(user);
        return gambler;
    }

    @GET("/gambler/{email}")
    @PermitAll
    public Gambler getGambler(String email) {
        Logger logger = Logger.getLogger(BetResource.class.getName());
        logger.log(Level.INFO, email);
        return gamblerService.getFromEmail(email);
    }

    @GET("/gamblers")
    @RolesAllowed(Roles.GAMBLER)
    public List<Gambler> getGamblers() {
        return gamblerService.getAll();
    }

    @GET("/gamblersTeam/{team}")
    @RolesAllowed(Roles.GAMBLER)
    public Set<Gambler> getGamblersTeam(String team) {
        Set<Gambler> toRet = new HashSet<>();
//        List<Gambler> gamblers = gamblerService.getAll();
//        for(Gambler gambler:gamblers){
//            for(StatutTeam statutTeam:gambler.getStatutTeams()){
//                if(statutTeam.getTeam().getName().equals(team)&&statutTeam.isAccepted()){
//                    toRet.add(gambler);
//                    break;
//                }
//            }
//        }
        return toRet;
    }

    @POST("/joiner")
    @RolesAllowed(Roles.GAMBLER)
    public Gambler postJoiner(Gambler gambler) {

        return gamblerService.updateGambler(gambler);
    }


    @POST("/performSubscription")
    @PermitAll
    public void subscribe(UserAndTeams subscriber) {
        Logger logger = Logger.getLogger(BetResource.class.getName());

        final String subject = "Confirmation d'inscription à Zen Foot";
        final String urlConfirmation = "<a href='" + getUrlConfirmation() + subscriber.getUser().getEmail() + "'> Confirmation d'inscription </a>";
        final String messageContent = "Mr, Mme " + subscriber.getUser().getNom() + " Merci de cliquer sur le lien ci-dessous pour confirmer votre inscription. \n\n" + urlConfirmation;
        subscriber.getUser().setRoles(Arrays.asList(Roles.GAMBLER));
        subscriber.getUser().setIsActive(Boolean.TRUE);
        subscriber.getUser().setPassword(subscriber.getUser().getPasswordHash());
//        subscriber.getUser().setIsActive(Boolean.FALSE);

        User existingUser = userService.getUserByEmail(subscriber.getUser().getEmail());

        if(existingUser==null){

            Key<User> keyUser = userService.createUser(subscriber.getUser());
            User user = userService.get(keyUser);
            Key<Gambler> gamblerKey = gamblerService.createGambler(user, matchService.getMatchs());
            Gambler gambler = gamblerService.getGambler(gamblerKey);

            Set<StatutTeam> testSet = new HashSet<>();

            gamblerService.addTeams(subscriber.getTeams(), gambler);
        }



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
    public List<GamblerRanking> rankings(){
        return rankingDAO.getAll();
    }

    @GET("/ranking")
    @RolesAllowed(Roles.GAMBLER)
    public GamblerRanking ranking(){
        Gambler gambler =gamblerService.get(sessionInfo.getUser());
        GamblerRanking gamblerRanking = rankingDAO.findByGambler(gambler.getId());
        return gamblerRanking;
    }

    @GET("/updatematchs")
    @RolesAllowed(Roles.ADMIN)
    public void updateMatchs(){
        DateTimeZone timezone = DateTimeZone.forID("Europe/Paris");

//        Match match1 = matchService.getMatch(566964810383360L);
        Match match1 = matchService.getMatch(6298689261797376L);

        match1.setDate(match1.getDate().plusDays(1));
        matchService.createUpdate(match1);


//        Match match2 = matchService.getMatch(6685468246671360L);
        Match match2 = matchService.getMatch(5203877938855936L);

        match2.setDate(match2.getDate().plusDays(1));
        matchService.createUpdate(match2);


//        Match match3 = matchService.getMatch(5508654186889216L);
        Match match3 = matchService.getMatch(6323463706902528L);

        match3.setDate(match3.getDate().plusDays(1));
        matchService.createUpdate(match3);


//        Match match4 = matchService.getMatch(6644476005056512L);
        Match match4 = matchService.getMatch(4841319885176832L);

        match4.setDate(match4.getDate().plusDays(1));
        matchService.createUpdate(match4);


//        Match match5 = matchService.getMatch(6629568978878464L);
        Match match5 = matchService.getMatch(5389504546340864L);

        match5.setDate(match5.getDate().plusDays(1));
        matchService.createUpdate(match5);


//        Match match6 = matchService.getMatch(5513576487845888L);
        Match match6 = matchService.getMatch(4837886058823680L);

        match6.setDate(match6.getDate().plusDays(1));
        matchService.createUpdate(match6);


        Match match7 = matchService.getMatch(6256522883497984L);
//        Match match7 = matchService.getMatch(4583914442063872L);

        match7.setDate(match7.getDate().plusDays(1));
        matchService.createUpdate(match7);


        Match match8 = matchService.getMatch(4916088823349248L);
//        Match match8 = matchService.getMatch(5117864776302592L);

        match8.setDate(match8.getDate().plusDays(1));
        matchService.createUpdate(match8);



        Match match9 = matchService.getMatch(5967219792019456L);

//        Match match9 = matchService.getMatch(5141891360227328L);
        match9.setDate(match9.getDate().plusDays(1));
        matchService.createUpdate(match9);


        Match match10 = matchService.getMatch(6515404453183488L);

//        Match match10 = matchService.getMatch(5697384176680960L);
        match10.setDate(match10.getDate().plusDays(1));
        matchService.createUpdate(match10);

//        Match match11 = matchService.getMatch(4960561330651136L);

        Match match11 = matchService.getMatch(5963785965666304L);
        match11.setDate(match11.getDate().plusDays(1));
        matchService.createUpdate(match11);

    }


}
