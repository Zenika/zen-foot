package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.user.User;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.List;
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


    public Gambler get(User user){
        return this.gamblerRepository.getGamblerFromEmail(user.getEmail());
    }

    public Gambler getFromEmail(String email){
        return this.gamblerRepository.getGamblerFromEmail(email);
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

    public void updateBets(List<Bet> newBets, Gambler gambler) {

        DateTime now = DateTime.now();


        for (Bet bet : newBets) {
            Bet existingBet = this.getBetByMatchId(gambler,bet.getMatchId());
            //Check the bet already existed in the database
            if(existingBet==null){
                Logger logger = Logger.getLogger(Gambler.class.getName());
                logger.log(Level.SEVERE,"tried to update a bet which didn't exist");
            }
            else {
                //If the bet has changed (after a user input), rewrite the object in the database
                if (!existingBet.exactSame(bet)) {
                    Match match = matchService.getMatch(bet.getMatchId());

                    //We have to check that the bet was made before the beginning of the match before registering it
                    if(!match.hasOccured(now)){
                        gambler.remove(existingBet);
                        gambler.addBet(bet);
                    }


                }
            }
        }
    }





    public Gambler createGambler(User user, List<Match> matchs){

        System.out.println("creating gambler with email "+user.getEmail());
        Gambler gambler = new Gambler(user.getEmail());
        Logger logger = Logger.getLogger(GamblerService.class.getName()+1);
        logger.log(Level.WARNING,"while creating gambler, there are "+matchs.size());
        for (Match match : matchs) {
            Bet bet = new Bet(match.getId());
            gambler.addBet(bet);

        }
        this.gamblerRepository.saveGambler(gambler);
        return this.get(user);
    }

    public Gambler updateGambler(Gambler gambler){
        gamblerRepository.saveGambler(gambler);
        return getFromEmail(gambler.getEmail());
    }

}
