/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenika.zenfoot.gae.dao;

import com.zenika.zenfoot.gae.IGenericDAO;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;
import java.util.List;

/**
 *
 * @author nebulis
 */
public interface BetDAO extends IGenericDAO<Bet> {
    
    Bet getBetByGamblerAndMatchId(Gambler gambler, Long matchId);
    
    List<Bet> getBetsByMatchId(Long matchId);
    
}
