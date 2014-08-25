package com.zenika.zenfoot.gae.rest;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import com.zenika.zenfoot.gae.model.*;
import com.zenika.zenfoot.gae.services.*;
import restx.RestxRequest;
import restx.RestxResponse;
import restx.WebException;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.*;

import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.user.User;


@RestxResource
@Component
public class BetResource {

    private Logger logger = Logger.getLogger(getClass().getName());
    private MatchService matchService;

    private SessionInfo sessionInfo;
    private GamblerService gamblerService;
    private MockUserService userService;

    public BetResource(MatchService matchService,
                       @Named("sessioninfo") SessionInfo sessionInfo,
                       @Named("userService") UserService userService,
                       GamblerService gamblerService
                       ) {

        this.sessionInfo = sessionInfo;
        this.matchService = matchService;
        this.userService = (MockUserService) userService;
        this.gamblerService = gamblerService;
    }


    @POST("/redirectAfterLogin")
    public void redirectAfterLogin() {
        throw new WebException(HttpStatus.FOUND) {
            @Override
            public void writeTo(RestxRequest restxRequest, RestxResponse restxResponse) throws IOException {
                restxResponse
                        .setStatus(getStatus())
                        .setHeader("Location", "/#/bets");
            }
        };
    }


    @GET("/bets")
    @RolesAllowed({Roles.GAMBLER})
    public List<Bet> getBets() {
        User user = sessionInfo.getUser();
        Gambler gambler = gamblerService.get(user);
        return gambler.getBets();
    }

    @POST("/bets")
    @RolesAllowed(Roles.GAMBLER)
    public void postBets(List<Bet> bets) {
        User user = sessionInfo.getUser();
        Gambler gambler = gamblerService.get(user);
        gamblerService.updateBets(bets, gambler);
    }

}
