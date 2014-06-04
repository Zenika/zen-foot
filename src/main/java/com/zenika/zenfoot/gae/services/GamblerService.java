package com.zenika.zenfoot.gae.services;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.model.*;

import com.zenika.zenfoot.user.User;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.ArrayList;
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

    public GamblerService(GamblerRepository gamblerRepository, MatchService matchService) {
        this.matchService=matchService;
        this.gamblerRepository = gamblerRepository;
    }

    public List<Gambler> getAll(){
        return gamblerRepository.getAll();
    }

    public Gambler get(User user){
        return this.getFromEmail(user.getEmail());
    }

    public Gambler getFromEmail(String email){

        Gambler gambler = this.gamblerRepository.getGamblerFromEmail(email);
        return gambler;
    }

    public void updateBets(Gambler gambler){
        List<Match> matchs = matchService.getMatchs();
        for(Match match:matchs){

            if(!this.hasBet(gambler,match.getId())){
                gambler.addBet(new Bet(match.getId()));
            }
        }
        gamblerRepository.saveGambler(gambler);

    }

    public Bet getBet(Gambler gambler, Match match) {
      return getBetByMatchId(gambler,match.getId());
    }

    public Bet getBetByMatchId(Gambler gambler, Long matchId) {
        Bet toRet = null;

        for (Bet bet : gambler.getBets()) {
            if (bet.getMatchId().equals(matchId)) {
                toRet = bet;
                break;
            }
        }
        return toRet;
    }

    public boolean hasBet(Gambler gambler, Long matchId){
        for(Bet bet:gambler.getBets()){
            if (bet.getMatchId().equals(matchId)) {
                return true;
            }

        }
        return false;
    }

    public Gambler updateBets(List<Bet> newBets, Gambler gambler) {

        DateTime now = DateTime.now();


        for (Bet bet : newBets) {

            Bet existingBet = this.getBetByMatchId(gambler,bet.getMatchId());
            //Check the bet already existed in the database
            Logger logger = Logger.getLogger(Gambler.class.getName());


            if(existingBet==null){
                logger.log(Level.SEVERE,""+bet.getMatchId());
                logger.log(Level.SEVERE,"tried to update a bet which didn't exist");
            }
            else {
                //If the bet has changed (after a user input), rewrite the object in the database
                if (!existingBet.exactSame(bet)) {

                    logger.log(Level.ALL,"try to register a new bet");
                    Match match = matchService.getMatch(bet.getMatchId());

                    //We have to check that the bet was made before the beginning of the match before registering it
                    if(!match.hasOccured(now)){
                        gambler.remove(existingBet);
                        gambler.addBet(bet);
                    }


                }
            }
        }

        return gambler;
    }


    public Key<Gambler> createGambler(User user, List<Match> matchs) {
        return createGambler(user,matchs,new HashSet<Team>());

    }

    public Key<Gambler> createGambler(User user, List<Match> matchs, Set<Team> teams){
        return createGambler(user,matchs,0,teams);
    }

    public Key<Gambler> createGambler(User user, List<Match> matchs, int points){
        return createGambler(user, matchs, points, new HashSet<Team>());
    }

    //TODO : remove once the mocked users are removed
    public Key<Gambler> createGambler(User user, List<Match> matchs, int points, Set<Team> teams){
        System.out.println("creating gambler with email " + user.getEmail());
        Gambler gambler = new Gambler(user.getEmail());
        gambler.setTeams(teams);
        gambler.setPrenom(user.getPrenom());
        gambler.setNom(user.getNom());
        gambler.addPoints(points);

        Logger logger = Logger.getLogger(GamblerService.class.getName() + 1);
        logger.log(Level.WARNING, "while creating gambler, there are " + matchs.size());
        for (Match match : matchs) {
            Bet bet = new Bet(match.getId());
            gambler.addBet(bet);
        }
        Key<Gambler> toRet= this.gamblerRepository.saveGambler(gambler);
        return toRet;
    }

    public Gambler updateGambler(Gambler gambler) {
        gamblerRepository.saveGambler(gambler);
        return getFromEmail(gambler.getEmail());
    }

    public Gambler getGambler(Key<Gambler> gamblerKey){
        return gamblerRepository.getGambler(gamblerKey);
    }

    public void calculateScores(Match match) {
        List<Gambler> gamblers = gamblerRepository.getAll();
        for(Gambler gambler:gamblers){
            Bet bet = this.getBet(gambler,match);
            if(bet.wasMade()){
                if(bet.isLike3Points(match.getOutcome())){
                    gambler.addPoints(3);
                }
                else{
                    if(bet.isLike1Point(match.getOutcome())){
                        gambler.addPoints(1);
                    }
                }
            }
        }
    }

    public void updateTeams(Set<Team> registeredTeams, Gambler gambler) {
        gambler.setTeams(registeredTeams);
        gamblerRepository.saveGambler(gambler);
    }
}
