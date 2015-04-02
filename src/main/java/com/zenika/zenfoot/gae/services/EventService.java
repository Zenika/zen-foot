package com.zenika.zenfoot.gae.services;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.dao.EventDAO;
import com.zenika.zenfoot.gae.dao.GamblerDAO;
import com.zenika.zenfoot.gae.dto.BetDTO;
import com.zenika.zenfoot.gae.dto.MatchDTO;
import com.zenika.zenfoot.gae.mapper.MapperFacadeFactory;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Match;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Created by raphael on 28/08/14.
 */
public class EventService {

    private Logger logger = Logger.getLogger(getClass().getName());
    

    private EventDAO eventDAO;
    private GamblerDAO gamblerDAO;
    private MapperFacadeFactory mapper;

    public EventService(EventDAO eventDAO, MapperFacadeFactory mapper, GamblerDAO gamblerDAO) {
        this.eventDAO = eventDAO;
        this.mapper = mapper;
        this.gamblerDAO = gamblerDAO;
    }

    /**
     * Checks whether an event has already been registered with this name
     * @param eventName
     * @return
     */
    public boolean contains(String eventName){
        List<Event> events = eventDAO.getAll();
        boolean toRet = false;
        if (events != null && events.size() > 0) {
            for(Event event : events){
                if(event.getName().equals(eventName)){
                    toRet = true;
                    break;
                }
            }
        }
        return toRet;
    }

    public Key<Event> createUpdate(Event event) {
        return this.eventDAO.createUpdate(event);
    }

    public List<Event> getAll() {
        return eventDAO.getAll();
    }

    public Event getEvent(Key<Event> eventKey) {
        return eventDAO.get(eventKey);
    }
    public void delete(Long id) {
        this.eventDAO.delete(id);
    }
    
    public Event get(Long id) {
        return this.eventDAO.get(id);
    }
    
    public List<MatchDTO> getMatches(Long id) {
        logger.warning("WARN");
        logger.info("INFO");
        logger.log(Level.FINER, "FINER");
        logger.log(Level.FINEST, "FINEST");
        
        Event event = this.eventDAO.get(id);
        List<Match> ms = this.eventDAO.getMatches(event);
        List<MatchDTO> matches = new ArrayList<MatchDTO>();
        mapper.getMapper().mapAsCollection(ms, matches, MatchDTO.class);
        return matches;
    }
    
    public List<BetDTO> getBets(Long id, String email) {
        Gambler gambler = this.getGamblerFromEventIdAndEmail(id, email);
        List<BetDTO> bets = new ArrayList<BetDTO>();
        if (gambler != null) {
            List<Bet> bs = this.gamblerDAO.getBets(gambler);
            mapper.getMapper().mapAsCollection(bs, bets, BetDTO.class);
        }
        return bets;
    }
    
    public Gambler getGamblerFromEventIdAndEmail(Long id, String email) {
        Event event = this.eventDAO.get(id);
        return this.gamblerDAO.getGamblerFromEmailAndEvent(email, event);
    }
}
