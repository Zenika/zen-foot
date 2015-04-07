/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.ObjectifyService;
import com.zenika.zenfoot.gae.GenericDAO;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;
import java.util.List;

/**
 *
 * @author nebulis
 */
public class BetDAOImpl extends GenericDAO<Bet> implements BetDAO {
    @Override
    public Bet getBetByGamblerAndMatchId(Gambler gambler, Long matchId) {
        return ObjectifyService.ofy().load().type(Bet.class).ancestor(gambler).filter("matchId", matchId).first().now();
    }    
    @Override
    public List<Bet> getBetsByMatchId(Long matchId) {
        return ObjectifyService.ofy().load().type(Bet.class).filter("matchId", matchId).list();
        
    }
}
