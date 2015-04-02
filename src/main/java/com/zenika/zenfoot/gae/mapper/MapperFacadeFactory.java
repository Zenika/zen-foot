/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenika.zenfoot.gae.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import restx.factory.Component;
import restx.factory.Provides;

/**
 *
 * @author nebulis
 */
@Component
public class MapperFacadeFactory {
    
    private MapperFacade mapper;
    private MapperFactory mapperFactory;
    
    private MatchDtoToMatchMapper matchDtoToMatchMapper;
    private GamblerDtoToGamblerMapper gamblerDtoToGamblerMapper;
    private BetDtoToBetMapper betDtoToBetMapper;

    public MapperFacadeFactory(MatchDtoToMatchMapper matchDtoToMatchMapper, GamblerDtoToGamblerMapper gamblerDtoToGamblerMapper,
            BetDtoToBetMapper betDtoToBetMapper) {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        mapper = mapperFactory.getMapperFacade();
        
        this.matchDtoToMatchMapper = matchDtoToMatchMapper;
        this.gamblerDtoToGamblerMapper = gamblerDtoToGamblerMapper;
        this.betDtoToBetMapper = betDtoToBetMapper;
        
        addCustomizeMapper();
    }	

    private void addCustomizeMapper() {
        mapperFactory.registerMapper(matchDtoToMatchMapper);
        mapperFactory.registerMapper(gamblerDtoToGamblerMapper);
        mapperFactory.registerMapper(betDtoToBetMapper);
    }

    public MapperFacade getMapper() {
        return mapper;
    }

    public void setMapper(MapperFacade mapper) {
        this.mapper = mapper;
    }

    public void setMatchDtoToMatchMapper(MatchDtoToMatchMapper matchDtoToMatchMapper) {
        this.matchDtoToMatchMapper = matchDtoToMatchMapper;
    }

}
