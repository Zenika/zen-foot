package com.zenika.zenfoot.gae.rest;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.dto.BetDTO;
import com.zenika.zenfoot.gae.dto.MatchDTO;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.services.*;
import com.zenika.zenfoot.user.User;
import restx.WebException;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.RolesAllowed;

import java.util.List;
import javax.inject.Named;
import restx.annotations.DELETE;
import restx.annotations.PUT;
import restx.security.PermitAll;

/**
 * Created by raphael on 25/08/14.
 */
@Component
@RestxResource
public class EventResource {

    private EventService eventService;
    private GamblerService gamblerService;
    private MatchService matchService;

    private SessionInfo sessionInfo;

    public EventResource(EventService eventService, @Named("sessioninfo")SessionInfo sessionInfo, GamblerService gamblerService,
            MatchService matchService) {
        this.eventService = eventService;
        this.sessionInfo = sessionInfo;
        this.gamblerService = gamblerService;
        this.matchService = matchService;
    }

    @POST("/events")
    @RolesAllowed(Roles.ADMIN)
    public Event createUpdate(Event event) {
        if (eventService.contains(event.getName())) {
            throw new WebException(HttpStatus.BAD_REQUEST);
        }
        Key<Event> eventKey = eventService.createUpdate(event);
        return eventService.getEvent(eventKey);
    }

    @GET("/events")
    @RolesAllowed({Roles.ADMIN, Roles.GAMBLER})
    public List<Event> eventNames() {
        return eventService.getAll();
    }

    @GET("/events/{id}/matches")
    @PermitAll
    public List<MatchDTO> getMatches(Long id) {
        return eventService.getMatches(id);
    }

    @GET("/events/{id}/bets")
    @PermitAll
    public List<BetDTO> getBets(Long id) {
        return eventService.getBets(id, sessionInfo.getUser().getEmail());
    }

    @POST("/events/{id}/bets")
    @RolesAllowed(Roles.GAMBLER)
    public void postBets(Long id, List<BetDTO> bets) {
        if (bets != null && bets.size() > 0) {
            // get gambler and event
            Event event = eventService.get(id);
            User user = sessionInfo.getUser();
            Gambler gambler = gamblerService.getFromEmailAndEvent(user.getEmail(), event);
            
            if (gambler == null) {
                Key<Gambler> eventGambler = gamblerService.createGambler(user, event);
                gambler = gamblerService.getGambler(eventGambler);
            }
            
            //save bets
            gamblerService.updateBets(bets, gambler, event);
        }
    }

    @DELETE("/events/{id}")
    @RolesAllowed(Roles.ADMIN)
    public void deleteEvent(Long id) {
        eventService.delete(id);
    }

    @PUT("/events/{id}")
    @RolesAllowed(Roles.ADMIN)
    public Event updateEvent(Long id, Event event) {
        if (eventService.contains(event.getName())) {
            Key<Event> eventKey = eventService.createUpdate(event);
            return eventService.getEvent(eventKey);
        } 
        throw new WebException(HttpStatus.BAD_REQUEST);
    }
    
    @POST("/events/{id}/matchs")
    @RolesAllowed(Roles.ADMIN)
    public void updateMatch(Long id, Match match) {
        Event event = eventService.get(id);
        Match matchFormerValue = matchService.getMatch(match.getId(), event);
        gamblerService.setScore(matchFormerValue,match);
        matchFormerValue.setScore1(match.getScore1());
        matchFormerValue.setScore2(match.getScore2());
        matchFormerValue.setScoreUpdated(true);
        matchService.createUpdate(matchFormerValue);
    }

}
