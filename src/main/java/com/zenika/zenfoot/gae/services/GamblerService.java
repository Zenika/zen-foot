package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.user.User;

/**
 * Created by raphael on 30/04/14.
 */
public class GamblerService {
    public Gambler get(User user) {
        return new Gambler();
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
}
