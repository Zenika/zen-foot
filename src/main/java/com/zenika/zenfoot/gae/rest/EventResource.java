package com.zenika.zenfoot.gae.rest;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.dto.BetDTO;
import com.zenika.zenfoot.gae.dto.GamblerDTO;
import com.zenika.zenfoot.gae.dto.LigueDTO;
import com.zenika.zenfoot.gae.dto.MatchDTO;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Ligue;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.services.*;
import com.zenika.zenfoot.gae.model.User;
import com.zenika.zenfoot.gae.utils.KeyBuilder;
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

    final private EventService eventService;
    final private LigueService ligueService;
    final private GamblerService gamblerService;
    final private MatchService matchService;
    final private SessionInfo sessionInfo;

    public EventResource(EventService eventService, @Named("sessioninfo") SessionInfo sessionInfo,
                         @Named("gamblerService") GamblerService gamblerService,
                         MatchService matchService, LigueService ligueService) {
        this.eventService = eventService;
        this.sessionInfo = sessionInfo;
        this.gamblerService = gamblerService;
        this.matchService = matchService;
        this.ligueService = ligueService;
    }

    @POST("/events")
    @RolesAllowed(Roles.ADMIN)
    public Event createUpdate(Event event) {
        if (eventService.contains(event.getName())) {
            throw new WebException(HttpStatus.BAD_REQUEST);
        }
        return eventService.getFromKey(
                eventService.createOrUpdate(event));
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

    @GET("/events/{id}/gamblers")
    @RolesAllowed(Roles.GAMBLER)
    public List<GamblerDTO> getGamblers(Long id) {
        return eventService.getGamblers(id);
    }

    @GET("/events/{id}/gambler")
    @RolesAllowed(Roles.GAMBLER)
    public GamblerDTO getGambler(Long id) {
        return eventService.getGambler(id, sessionInfo.getUser().getEmail());
    }

    @GET("/events/{id}/bets")
    @RolesAllowed(Roles.GAMBLER)
    public List<BetDTO> getBets(Long id) {
        return eventService.getBets(id, sessionInfo.getUser().getEmail());
    }

    @POST("/events/{id}/bets")
    @RolesAllowed(Roles.GAMBLER)
    public void postBets(Long id, List<BetDTO> bets) {
        if (bets != null && bets.size() > 0) {
            // get gambler and event
            Event event = eventService.getFromID(id);
            User user = sessionInfo.getUser();
            Gambler gambler = gamblerService.getGamblerFromEmailAndEvent(user.getEmail(), event);

            if (gambler == null) {
                gambler = gamblerService.createOrUpdateAndReturn(user, event);
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
            return eventService.createOrUpdateAndReturn(event);
        }
        throw new WebException(HttpStatus.BAD_REQUEST);
    }

    @PUT("/events/{id}/matchs")
    @RolesAllowed(Roles.ADMIN)
    public void updateMatch(Long id, Match match) {
        Event event = eventService.getFromID(id);
        Match matchFormerValue = matchService.getMatch(match.getId(), event);
        gamblerService.setScore(matchFormerValue, match);
        matchFormerValue.setScore1(match.getScore1());
        matchFormerValue.setScore2(match.getScore2());
        matchFormerValue.setScoreUpdated(true);
        matchService.createOrUpdate(matchFormerValue);
    }

    @POST("/events/{id}/matchs")
    @RolesAllowed(Roles.ADMIN)
    public void createMatch(Long id, Match match) {
        Key<Event> keyEvent = Key.create(Event.class, id);
        match.setEvent(keyEvent);
        matchService.createOrUpdate(match);
    }

    /**
     * ligue
     */


    @GET("/events/{id}/ligues")
    @RolesAllowed(Roles.GAMBLER)
    public List<LigueDTO> getLigues(Long id) {
        return eventService.getLigues(id, sessionInfo.getUser().getEmail());
    }

    @GET("/events/{id}/ligues/{idLigue}")
    @RolesAllowed(Roles.GAMBLER)
    public LigueDTO getLigue(Long id, Long idLigue) {
        return eventService.getLigue(id, idLigue, sessionInfo.getUser().getEmail());
    }

    @PUT("/events/{id}/ligues/{idLigue}")
    @RolesAllowed(Roles.GAMBLER)
    public void updateLigue(Long id, Long idLigue, LigueDTO ligue) {
        Event event = eventService.getFromID(id);
        ligueService.update(event, ligue);
    }

    @POST("/events/{id}/ligues/{IdLigue}/join")
    @RolesAllowed(Roles.GAMBLER)
    public void joinLigue(Long id, Long IdLigue) {
        Event event = eventService.getFromID(id);
        Gambler gambler = eventService.getGamblerFromEventIdAndEmail(id, sessionInfo.getUser().getEmail());
        if (gambler == null) {
            gambler = gamblerService.createOrUpdateAndReturn(sessionInfo.getUser(), event);
        }
        Ligue ligue = ligueService.getFromKey(KeyBuilder.buildLigueKey(IdLigue, id));
        ligueService.joinLigue(ligue, gambler, event);
    }

    @POST("/events/{id}/ligues")
    @RolesAllowed(Roles.GAMBLER)
    public void createUpdate(Long id, LigueDTO ligue) {
        Event event = eventService.getFromID(id);
        Gambler gambler = eventService.getGamblerFromEventIdAndEmail(id, sessionInfo.getUser().getEmail());
        if (gambler == null) {
            gambler = gamblerService.createOrUpdateAndReturn(sessionInfo.getUser(), event);
        }

        ligueService.create(event, ligue, gambler);

    }
}
