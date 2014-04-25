package com.zenika.zenfoot.gae.rest;

import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.services.BetRepository;
import com.zenika.zenfoot.gae.services.SessionInfo;
import com.zenika.zenfoot.user.User;
import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import restx.security.RolesAllowed;

import javax.inject.Named;
import java.util.List;

@RestxResource
@Component
public class BetResource {


    private BetRepository betRepository;

     private SessionInfo sessionInfo;

    public BetResource(@Named("betrepository") BetRepository betRepository
                       , @Named("sessioninfo") SessionInfo sessionInfo
    ) {
        this.betRepository = betRepository;
             this.sessionInfo = sessionInfo;
    }


    @GET("/hello")
    @RolesAllowed(Roles.ADMIN)
    public String getMessage1() {
        return "Hello !";
    }

    @GET("/coucou")
    @RolesAllowed(Roles.GAMBLER)
    public String getCoucou() {
        return "coucou";
    }


    @GET("/bets")
    @PermitAll
    public List<Bet> getBets() {
        User user =sessionInfo.getUser();
        List<Bet> bets = betRepository.getBets(user);
        for(int i=0;i<20;i++) {
            System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOo");
        }
        return bets;
    }

}
