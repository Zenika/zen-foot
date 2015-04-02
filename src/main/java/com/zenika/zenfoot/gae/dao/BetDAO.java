/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;

/**
 *
 * @author nebulis
 */
public interface BetDAO {
    
    Bet getBetByGamblerAndMatchId(Gambler gambler, Long matchId);
    
    Key<Bet> createUpdate(Bet bet);
    
}
