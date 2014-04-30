package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;

import java.util.List;

/**
 * Created by raphael on 30/04/14.
 */
public class BetService {

    private BetRepository betRepository;

    public BetService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

public void save(List<Bet> bets, Gambler gambler) {
        //logic on how to add bets (not betting on matchs that are already done/are currently happening.
        List<Bet> formerBets = gambler.getBets();
        
    }
}
