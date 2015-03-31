package com.zenika.zenfoot.gae.rest;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.dto.MatchDTO;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.services.*;
import restx.WebException;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.RolesAllowed;

import java.util.List;
import restx.annotations.DELETE;
import restx.annotations.PUT;

/**
 * Created by raphael on 25/08/14.
 */
@Component
@RestxResource

public class EventResource {

    private EventService eventService;

    public EventResource(EventService eventService) {
        this.eventService = eventService;
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
    @RolesAllowed(Roles.ADMIN)
    public List<Event> eventNames() {
        return eventService.getAll();
    }

    @GET("/events/{id}/matches")
    @RolesAllowed(Roles.ADMIN)
    public List<MatchDTO> getMatches(Long id) {
        return eventService.getMatches(id);
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

}
