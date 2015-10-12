/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenika.zenfoot.gae.mapper;

import com.zenika.zenfoot.gae.dto.LightLigueDTO;
import com.zenika.zenfoot.gae.dto.UserDTO;
import com.zenika.zenfoot.gae.model.Ligue;
import com.zenika.zenfoot.gae.model.User;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import restx.factory.Component;

/**
 *
 * @author nebulis
 */
@Component
public class MapperFacadeFactory {
    
    private MapperFacade mapper;
    private MapperFactory mapperFactory;
    
    public MapperFacadeFactory(MatchDtoToMatchMapper matchDtoToMatchMapper, GamblerDtoToGamblerMapper gamblerDtoToGamblerMapper,
            BetDtoToBetMapper betDtoToBetMapper, LigueDtoToLigueMapper ligueDtoToLigueMapper) {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        mapper = mapperFactory.getMapperFacade();
        
        addCustomMapper(matchDtoToMatchMapper);
        addCustomMapper(gamblerDtoToGamblerMapper);
        addCustomMapper(betDtoToBetMapper);
        addCustomMapper(ligueDtoToLigueMapper);
        mapperFactory.classMap(User.class, UserDTO.class).byDefault().register();
        mapperFactory.classMap(Ligue.class, LightLigueDTO.class).byDefault().register();
        
    }

    private void addCustomMapper(CustomMapper<?, ?> mapper) {
        if(mapper != null){
            mapperFactory.registerMapper(mapper);
        }
    }

    public MapperFacade getMapper() {
        return mapper;
    }

    public void setMapper(MapperFacade mapper) {
        this.mapper = mapper;
    }

}
