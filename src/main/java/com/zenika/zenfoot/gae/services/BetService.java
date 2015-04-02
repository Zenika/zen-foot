package com.zenika.zenfoot.gae.services;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.dao.BetDAO;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;

/**
 * Created by raphael on 30/04/14.
 */
public class BetService {
    
    BetDAO betDao;

    public BetService(BetDAO betDao) {
        this.betDao = betDao;
    }
    
    public Bet getBetByMatchId(Gambler gambler, Long matchId) {
        return this.betDao.getBetByGamblerAndMatchId(gambler, matchId);
    }
    
    public Key<Bet> createUpdate(Bet bet) {
        return this.betDao.createUpdate(bet);
    }


}
