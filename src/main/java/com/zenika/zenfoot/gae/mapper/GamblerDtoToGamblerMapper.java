/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenika.zenfoot.gae.mapper;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.dto.GamblerDTO;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

/**
 *
 * @author nebulis
 */
public class GamblerDtoToGamblerMapper extends CustomMapper<GamblerDTO, Gambler> {
    
    public GamblerDtoToGamblerMapper() {
    }
    
    @Override
    public void mapAtoB(GamblerDTO a, Gambler b, MappingContext context) {
        b.setEvent(
                Key.create(Event.class, a.getEvent().getId())
        );
        b.setId(a.getId());
        b.setPrenom(a.getPrenom());
        b.setNom(a.getNom());
        b.setEmail(a.getEmail());
        b.setPoints(a.getPoints());
    }
    
    @Override
    public void mapBtoA(Gambler a, GamblerDTO b, MappingContext context) {
        b.setEvent(null);
        b.setId(a.getId());
        b.setPrenom(a.getPrenom());
        b.setNom(a.getNom());
        b.setEmail(a.getEmail());
        b.setBets(null);
        b.setDemandes(null);
        b.setStatutTeams(null);
        b.setPoints(a.getPoints());
    }
}
