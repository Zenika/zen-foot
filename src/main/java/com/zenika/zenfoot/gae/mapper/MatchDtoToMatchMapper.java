/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenika.zenfoot.gae.mapper;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.dto.MatchDTO;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.services.EventService;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import restx.factory.Component;

/**
 *
 * @author nebulis
 */
@Component
public class MatchDtoToMatchMapper extends CustomMapper<MatchDTO, Match> {
    
    public MatchDtoToMatchMapper() {
    }
    
    @Override
    public void mapAtoB(MatchDTO a, Match b, MappingContext context) {
        b.setEvent(
                Key.create(Event.class, a.getEvent().getId())
        );
        b.setId(a.getId());
        b.setDate(a.getDate());
        b.setGroupe(a.getGroupe());
        b.setScore1(a.getScore1());
        b.setScore2(a.getScore2());
        b.setScoreUpdated(a.isScoreUpdated());
        b.setTeam1(a.getTeam1());
        b.setTeam2(a.getTeam2());
    }
    
    @Override
    public void mapBtoA(Match a, MatchDTO b, MappingContext context) {
        b.setEvent(null);
        b.setId(a.getId());
        b.setDate(a.getDate());
        b.setGroupe(a.getGroupe());
        b.setScore1(a.getScore1());
        b.setScore2(a.getScore2());
        b.setScoreUpdated(a.isScoreUpdated());
        b.setTeam1(a.getTeam1());
        b.setTeam2(a.getTeam2());
    }
}
