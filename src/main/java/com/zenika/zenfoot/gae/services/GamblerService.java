package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.user.User;

import java.util.HashMap;
import java.util.List;

/**
 * Created by raphael on 30/04/14.
 */
public class GamblerService {

    private HashMap<String, Gambler> gamblers;

    public GamblerService(){
        this.gamblers=new HashMap<>();
    }

    public Gambler get(User user) {
        return this.gamblers.get(user.getEmail());
    }

    public Bet getBet(Gambler gambler, Match match){
        Bet toRet=null;

        for(Bet bet:gambler.getBets()){
            if(bet.getMatchId().equals(match.getId())){
                toRet = bet;
                break;
            }
        }
        return toRet;
    }

    public Gambler createGambler(User user, List<Match> matchs){
        Gambler gambler = new Gambler(user.getEmail());
        for(Match match:matchs){
            Bet bet = new Bet(match.getId());
            gambler.addBet(bet);
        }
        this.gamblers.put(user.getEmail(),gambler);
        return gambler;
    }
}
