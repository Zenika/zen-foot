/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenika.zenfoot.gae.mapper;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.zenika.zenfoot.gae.dto.GamblerDTO;
import com.zenika.zenfoot.gae.dto.LigueDTO;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Ligue;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.utils.KeyBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

/**
 * @author nebulis
 */
public class LigueDtoToLigueMapper extends CustomMapper<LigueDTO, Ligue> {
    private final GamblerService gamblerService;

    public LigueDtoToLigueMapper(GamblerService gamblerService) {
        this.gamblerService = gamblerService;
    }

    @Override
    public void mapAtoB(LigueDTO a, Ligue b, MappingContext context) {
        b.setOwner(KeyBuilder.buildGamblerKey(a.getOwner().getId(), a.getEvent().getId()));
        b.setId(a.getId());
        b.setEvent(Key.create(Event.class, a.getEvent().getId()));
        b.setName(a.getName());

        b.setAccepted(null);
        if (a.getAccepted() != null) {
            b.setAccepted(new ArrayList<Ref<Gambler>>(a.getAccepted().length));
            for (GamblerDTO gambler : a.getAccepted()) {
                b.getAccepted().add(KeyBuilder.buildGamblerRef(gambler.getId(), a.getEvent().getId()));
            }
        }

        b.setAwaits(null);
        if (a.getAwaits() != null) {
            b.setAwaits(new ArrayList<Ref<Gambler>>(a.getAwaits().length));
            for (GamblerDTO gambler : a.getAwaits()) {
                b.getAwaits().add(KeyBuilder.buildGamblerRef(gambler.getId(), a.getEvent().getId()));
            }
        }
    }

    @Override
    public void mapBtoA(Ligue a, LigueDTO b, MappingContext context) {
        Gambler owner = gamblerService.getFromKey(a.getOwner());
        b.setOwner(this.mapperFacade.map(owner, GamblerDTO.class));
        b.setId(a.getId());
        b.setEvent(null);
        b.setName(a.getName());

        b.setAccepted(null);
        if (a.getAccepted() != null) {
            int size = a.getAccepted().size();
            b.setAccepted(new GamblerDTO[size]);
            for (int i = 0; i < size; i++) {
                b.getAccepted()[i] = this.mapperFacade.map(this.gamblerService.getFromKey(a.getAccepted().get(i).getKey()), GamblerDTO.class);
            }
        }

        b.setAwaits(null);
        if (a.getAwaits() != null) {
            int size = a.getAwaits().size();
            b.setAwaits(new GamblerDTO[size]);
            for (int i = 0; i < size; i++) {
                b.getAwaits()[i] = this.mapperFacade.map(this.gamblerService.getFromKey(a.getAwaits().get(i).getKey()), GamblerDTO.class);
            }
        }
    }
}
