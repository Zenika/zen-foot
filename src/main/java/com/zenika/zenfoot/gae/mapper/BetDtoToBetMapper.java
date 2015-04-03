/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenika.zenfoot.gae.mapper;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.dto.BetDTO;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Gambler;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

/**
 *
 * @author nebulis
 */
public class BetDtoToBetMapper extends CustomMapper<BetDTO, Bet> {
    
    public BetDtoToBetMapper() {
    }
    
    @Override
    public void mapAtoB(BetDTO a, Bet b, MappingContext context) {
        b.setGambler(null);
        b.setId(a.getId());
        b.setMatchId(a.getMatchId());
        b.setScore1(a.getScore1());
        b.setScore2(a.getScore2());
    }
    
    @Override
    public void mapBtoA(Bet a, BetDTO b, MappingContext context) {
        b.setGambler(null);
        b.setId(a.getId());
        b.setMatchId(a.getMatchId());
        b.setScore1(a.getScore1());
        b.setScore2(a.getScore2());
    }
}
