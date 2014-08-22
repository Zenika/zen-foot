package com.zenika.zenfoot.gae.rest;

import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.dao.RankingDAO;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.GamblerRanking;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.SessionInfo;
import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.RolesAllowed;

import javax.inject.Named;
import java.util.List;

/**
 * Created by raphael on 11/08/14.
 */
@RestxResource
@Component
public class RankingResource {

    private RankingDAO rankingDAO;

    private GamblerService gamblerService;

    private SessionInfo sessionInfo;

    public RankingResource(RankingDAO rankingDAO, GamblerService gamblerService, @Named("sessioninfo") SessionInfo sessionInfo) {
        this.rankingDAO = rankingDAO;
        this.gamblerService = gamblerService;
        this.sessionInfo = sessionInfo;
    }

    @GET("/rankings")
    @RolesAllowed(Roles.GAMBLER)
    public List<GamblerRanking> rankings() {
        return rankingDAO.getAll();
    }

    @GET("/ranking")
    @RolesAllowed(Roles.GAMBLER)
    public GamblerRanking ranking() {
        Gambler gambler = gamblerService.get(sessionInfo.getUser());
        return rankingDAO.findByGambler(gambler.getId());
    }

}
