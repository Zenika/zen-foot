package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.GenericDAO;
import com.zenika.zenfoot.gae.dao.BetDAO;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;

/**
 * Created by raphael on 30/04/14.
 */
public class BetService extends AbstractGenericService<Bet> {

    public BetService(BetDAO dao) {
        super(dao);
    }
    
    public Bet getBetByMatchId(Gambler gambler, Long matchId) {
        return ((BetDAO)this.getDao()).getBetByGamblerAndMatchId(gambler, matchId);
    }
    
}
