package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.model.*;
import com.zenika.zenfoot.user.User;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by raphael on 30/04/14.
 */
public class GamblerService {

    private GamblerRepository gamblerRepository;

    public GamblerService(GamblerRepository gamblerRepository) {
        this.gamblerRepository = gamblerRepository;
    }


    public Gambler get(User user) {
        return this.gamblerRepository.getGamblerFromEmail(user.getEmail());
    }

    public Gambler getFromEmail(String email) {
        return this.gamblerRepository.getGamblerFromEmail(email);
    }

    public Bet getBet(Gambler gambler, Match match) {
        Bet toRet = null;


        for (Bet bet : gambler.getBets()) {
            if (bet.getMatchId().equals(match.getId())) {
                toRet = bet;
                break;
            }
        }
        return toRet;
    }


    public Gambler createGambler(User user, List<Match> matchs) {

        System.out.println("creating gambler with email " + user.getEmail());
        Gambler gambler = new Gambler(user.getEmail());
        Logger logger = Logger.getLogger(GamblerService.class.getName() + 1);
        logger.log(Level.WARNING, "while creating gambler, there are " + matchs.size());
        for (Match match : matchs) {

            Bet bet = new Bet(match.getId());
            //TODO remove this :
            if (match.getParticipant1().equals(new Participant().setPays("Croatie"))
                    && match.getParticipant2().equals(new Participant().setPays("Bresil"))) {
                bet.setScore1(new Score().setScore(1));
                bet.setScore2(new Score().setScore(3));
            }

            gambler.addBet(bet);

        }
        this.gamblerRepository.saveGambler(gambler);
        return this.get(user);
    }

    public Gambler updateGambler(Gambler gambler) {
        gamblerRepository.saveGambler(gambler);
        return getFromEmail(gambler.getEmail());
    }

}
