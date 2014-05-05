package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.user.User;

import java.util.List;

/**
 * Created by raphael on 30/04/14.
 */
public class GamblerService {

    private GamblerRepository gamblerRepository;

    public GamblerService(GamblerRepository gamblerRepository) {
        this.gamblerRepository = gamblerRepository;
    }


    public Gambler get(User user){
        return this.gamblerRepository.getGamblerFromEmail(user.getEmail());
    }

    public Gambler getFromEmail(String email){
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





    public Gambler createGambler(User user, List<Match> matchs){

        System.out.println("creating gambler with email "+user.getEmail());
        Gambler gambler = new Gambler(user.getEmail());
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
