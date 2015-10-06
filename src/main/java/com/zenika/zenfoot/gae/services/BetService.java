package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.dao.BetDAO;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;
import java.util.List;

/**
 * Created by raphael on 30/04/14.
 */
public class BetService extends AbstractGenericService<Bet, Long> {

    public BetService(BetDAO dao) {
        super(dao);
    }
    
    public Bet getBetByGamblerAndMatchId(Gambler gambler, Long matchId) {
        return ((BetDAO)this.getDao()).getBetByGamblerAndMatchId(gambler, matchId);
    }
    
    public List<Bet> getBetsByMatchId(Long matchId) {
        return ((BetDAO)this.getDao()).getBetsByMatchId(matchId);
    }
    
}
