package com.zenika.zenfoot.gae.rest;

import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.dao.EventDAO;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.services.GamblerRankingService;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.MatchService;
import com.zenika.zenfoot.gae.services.TeamRankingService;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.RolesAllowed;

import java.util.ArrayList;

/**
 * Created by raphael on 25/08/14.
 */
@Component
@RestxResource

public class AdminResource {

    private EventDAO eventDAO;
    private GamblerService gamblerService;
    private MatchService matchService;
    private GamblerRankingService gamblerRankingService;
    private TeamRankingService teamRankingService;

    public AdminResource(EventDAO eventDAO, GamblerService gamblerService, MatchService matchService,
                         GamblerRankingService gamblerRankingService, TeamRankingService teamRankingService) {
        this.eventDAO = eventDAO;
        this.gamblerService = gamblerService;
        this.matchService = matchService;
        this.gamblerRankingService = gamblerRankingService;
        this.teamRankingService = teamRankingService;
    }

    @POST("/archive")
    @RolesAllowed(Roles.ADMIN)
    public void archive(StringWrapper eventName) {
        Event event = new Event();
        event.setName(eventName.getString());
        event.setGamblerRankings(gamblerRankingService.getAll());
        event.setMatches(matchService.getAll());
        event.setTeamRankings(teamRankingService.getAll());

        eventDAO.save(event);

        //Deleting what's been registered on the current competition
        //Deleting all bets
        for (Gambler gambler : gamblerService.getAll()) {
            gambler.setBets(new ArrayList<Bet>());
            gamblerService.updateGambler(gambler);
        }

        //Deleting matches
        matchService.deleteAll();

        //Reinitializing points to 0 for gamblers, and teams
        gamblerRankingService.reinitializePoints();
        teamRankingService.reinitializePoints();
    }

}
