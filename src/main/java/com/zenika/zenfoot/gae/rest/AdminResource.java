package com.zenika.zenfoot.gae.rest;

import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.GamblerBets;
import com.zenika.zenfoot.gae.services.*;
import restx.WebException;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.RolesAllowed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raphael on 25/08/14.
 */
@Component
@RestxResource

public class AdminResource {

    private GamblerService gamblerService;
    private MatchService matchService;
    private GamblerRankingService gamblerRankingService;
    private TeamRankingService teamRankingService;
    private EventService eventService;

    public AdminResource(GamblerService gamblerService, MatchService matchService,
                         GamblerRankingService gamblerRankingService, TeamRankingService teamRankingService, EventService eventService) {
        this.gamblerService = gamblerService;
        this.matchService = matchService;
        this.gamblerRankingService = gamblerRankingService;
        this.teamRankingService = teamRankingService;
        this.eventService = eventService;
    }

    @POST("/archive")
    @RolesAllowed(Roles.ADMIN)
    public void archive(StringWrapper eventName) {
        if (eventService.contains(eventName.getString())) {
            throw new WebException(HttpStatus.BAD_REQUEST);
        }

        Event event = new Event();
        event.setName(eventName.getString());
        event.setGamblerRankings(gamblerRankingService.getAll());
        event.setMatches(matchService.getAll());
        event.setTeamRankings(teamRankingService.getAll());
        event.setGamblerBetsList(generateGamblerBets());

        eventService.save(event);

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

    public List<GamblerBets> generateGamblerBets() {
        List<GamblerBets> gamblerBetsList = new ArrayList<>();

        for (Gambler gambler : gamblerService.getAll()) {
            GamblerBets gamblerBets = new GamblerBets(gambler);
            gamblerBetsList.add(gamblerBets);
        }

        return gamblerBetsList;
    }

    @GET("/eventNames")
    @RolesAllowed(Roles.ADMIN)
    public List<Event> eventNames() {
        return eventService.getAll();
    }

}
