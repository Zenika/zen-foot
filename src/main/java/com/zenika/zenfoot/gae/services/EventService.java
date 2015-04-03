package com.zenika.zenfoot.gae.services;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.GenericDAO;
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
public class EventService extends AbstractGenericService<Event> {

    final private Logger logger = Logger.getLogger(getClass().getName());
    
    final private GamblerService gamblerService;
    final private MapperFacadeFactory mapper;

    public EventService(EventDAO dao, MapperFacadeFactory mapper, GamblerService gamblerService) {
        super(dao);
        this.mapper = mapper;
        this.gamblerService = gamblerService;
    }

    /**
     * Checks whether an event has already been registered with this name
     * @param eventName
     * @return
     */
    public boolean contains(String eventName){
        List<Event> events = this.getDao().getAll();
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
    
    public List<MatchDTO> getMatches(Long id) {
        logger.warning("WARN");
        logger.info("INFO");
        logger.log(Level.FINER, "FINER");
        logger.log(Level.FINEST, "FINEST");
        
        Event event = this.getDao().findById(id);
        List<Match> ms = ((EventDAO)this.getDao()).getMatches(event);
        List<MatchDTO> matches = new ArrayList<MatchDTO>();
        mapper.getMapper().mapAsCollection(ms, matches, MatchDTO.class);
        return matches;
    }
    
    public List<BetDTO> getBets(Long id, String email) {
        Gambler gambler = this.getGamblerFromEventIdAndEmail(id, email);
        List<BetDTO> bets = new ArrayList<BetDTO>();
        if (gambler != null) {
            List<Bet> bs = this.gamblerService.getBets(gambler);
            mapper.getMapper().mapAsCollection(bs, bets, BetDTO.class);
        }
        return bets;
    }
    
    public Gambler getGamblerFromEventIdAndEmail(Long id, String email) {
        Event event = ((EventDAO)this.getDao()).findById(id);
        return this.gamblerService.getGamblerFromEmailAndEvent(email, event);
    }
}
