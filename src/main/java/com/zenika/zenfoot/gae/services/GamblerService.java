package com.zenika.zenfoot.gae.services;

import com.google.common.base.Optional;
import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.model.*;

import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.user.User;
import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by raphael on 30/04/14.
 */
public class GamblerService {

    private GamblerRepository gamblerRepository;
    private MatchService matchService;
    private TeamDAO teamDAO;

    public GamblerService(GamblerRepository gamblerRepository, MatchService matchService, TeamDAO teamDAO) {
        this.matchService = matchService;
        this.gamblerRepository = gamblerRepository;
        this.teamDAO=teamDAO;
    }

    public List<Gambler> getAll() {
        return gamblerRepository.getAll();
    }

    public Gambler get(User user) {
        return this.getFromEmail(user.getEmail());
    }

    public Gambler getFromEmail(String email) {

        Gambler gambler = this.gamblerRepository.getGamblerFromEmail(email);
        return gambler;
    }

    public void updateBets(Gambler gambler) {
//        List<Match> matchs = matchService.getMatchs();
//        for (Match match : matchs) {
//
//            if (!this.hasBet(gambler, match.getId())) {
//                gambler.addBet(new Bet(match.getId()));
//            }
//        }
//        gamblerRepository.saveGambler(gambler);

    }

    public Bet getBet(Gambler gambler, Match match) {
        return getBetByMatchId(gambler, match.getId());
    }

    public Bet getBetByMatchId(Gambler gambler, Long matchId) {
        Bet toRet = null;

//        for (Bet bet : gambler.getBets()) {
//            if (bet.getMatchId().equals(matchId)) {
//                toRet = bet;
//                break;
//            }
//        }
        return toRet;
    }

    public boolean hasBet(Gambler gambler, Long matchId) {
//        for (Bet bet : gambler.getBets()) {
//            if (bet.getMatchId().equals(matchId)) {
//                return true;
//            }
//
//        }
        return false;
    }

    public void updateBets(List<Bet> newBets, Gambler gambler) {

//        DateTime now = DateTime.now();
//
//
//        for (Bet bet : newBets) {
//
//            Bet existingBet = this.getBetByMatchId(gambler, bet.getMatchId());
//            //Check the bet already existed in the database
//            Logger logger = Logger.getLogger(Gambler.class.getName());
//
//
//
//            if (existingBet == null) {
//                logger.log(Level.SEVERE, "" + bet.getMatchId());
//                logger.log(Level.SEVERE, "tried to update a bet which didn't exist");
//            } else {
//                //If the bet has changed (after a user input), rewrite the object in the database
//                if (!existingBet.exactSame(bet)) {
//
//                    logger.log(Level.ALL, "try to register a new bet");
//                    Match match = matchService.getMatch(bet.getMatchId());
//
//                    //We have to check that the bet was made before the beginning of the match before registering it
//                    if (!match.hasOccured(now)) {
//                        gambler.remove(existingBet);
//                        gambler.addBet(bet);
//                    }
//
//
//                }
//            }
//        }
    }




    public Key<Gambler> createGambler(User user, List<Match> matchs){
        return createGambler(user,matchs,0);
    }


    //TODO : remove once the mocked users are removed

    public Key<Gambler> createGambler(User user, List<Match> matchs, int points){
//        System.out.println("creating gambler with email " + user.getEmail());
        Gambler gambler = new Gambler(user.getEmail());
//        gambler.setPrenom(user.getPrenom());
//        gambler.setNom(user.getNom());
////        gambler.addPoints(points);
//
//        Logger logger = Logger.getLogger(GamblerService.class.getName() + 1);
//        logger.log(Level.WARNING, "while creating gambler, there are " + matchs.size());
//        for (Match match : matchs) {
//            Bet bet = new Bet(match.getId());
//            gambler.addBet(bet);
//        }
//
        Key<Gambler> toRet= this.gamblerRepository.saveGambler(gambler);
        return toRet;
    }

    public Gambler updateGambler(Gambler gambler) {
        Key<Gambler> key = gamblerRepository.saveGambler(gambler);
        return gamblerRepository.getGambler(key);
    }

    public Gambler getGambler(Key<Gambler> gamblerKey){
        return gamblerRepository.getGambler(gamblerKey);
    }

    public void calculateScores(Match match) {

//        Logger logger = Logger.getLogger(GamblerService.class.getName());
//        logger.log(Level.INFO, "entering calculateScores");
//        List<Gambler> gamblers = gamblerRepository.getAll();
//        for (Gambler gambler : gamblers) {
//            Bet bet = this.getBet(gambler, match);
//            if (bet.wasMade()) {
//                if (bet.isLike3Points(match.getOutcome())) {
//                    gambler.addPoints(3);
//                } else {
//                    if (bet.isLike1Point(match.getOutcome())) {
//                        gambler.addPoints(1);
//                    }
//                }
//                if (gambler.getEmail().equals("jean.bon@zenika.com")) {
//                    logger.log(Level.INFO, "Points calculated for Jean Bon : " + gambler.getPoints());
//                }
//                this.updateGambler(gambler);
//
//            }
//        }


    }

    public Key<Gambler> addTeams(List<Team> teams, Gambler gambler){
        Logger logger = Logger.getLogger(GamblerService.class.getName());


        Set<StatutTeam> toReg = new HashSet<>();
        for (Team team :teams) {
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
            toReg.add(new StatutTeam().setTeam(toRegister).setAccepted(owner));

        }
//        gambler.addTeams(toReg);
        return gamblerRepository.saveGambler(gambler);

    }

    public void updateTeams(Set<StatutTeam> registeredTeams, Gambler gambler) {

        gamblerRepository.saveGambler(gambler);
    }

    /**
     * Get all the teams whose owner is gambler
     * @param gambler
     * @return
     */
    private Set<Team> isOwnerOf(Gambler gambler){
        Set<Team> set = new HashSet<>();
//        for(StatutTeam statutTeam:gambler.getStatutTeams()){
//            Team team = statutTeam.getTeam();
//            if(team.getOwnerEmail().equals(gambler.getEmail())){
//                set.add(team);
//            }
//        }
        return set;
    }

    /**
     * Get all the
     * @param gambler
     * @return
     */
    public Set<Gambler> wantToJoin(Gambler gambler) {
        Set<Team> ownedTeams = isOwnerOf(gambler);
        Set<Gambler> joining = new HashSet<>();

        for(Team team : ownedTeams){
            joining.addAll(gamblerRepository.wantToJoin(team.getName()));
        }

        return joining;
    }



}
