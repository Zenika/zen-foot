package com.zenika.zenfoot.gae.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.dao.EventDAO;
import com.zenika.zenfoot.gae.dto.BetDTO;
import com.zenika.zenfoot.gae.dto.GamblerDTO;
import com.zenika.zenfoot.gae.dto.LigueDTO;
import com.zenika.zenfoot.gae.dto.MatchDTO;
import com.zenika.zenfoot.gae.mapper.MapperFacadeFactory;
import com.zenika.zenfoot.gae.model.*;
import com.zenika.zenfoot.gae.utils.KeyBuilder;

/**
 * Created by raphael on 28/08/14.
 */
public class EventService extends AbstractGenericService<Event, Long> {

    final private Logger logger = Logger.getLogger(getClass().getName());

    final private GamblerService gamblerService;
    final private LigueService ligueService;
    final private MapperFacadeFactory mapper;

    public EventService(EventDAO dao, MapperFacadeFactory mapper, GamblerService gamblerService,
                        LigueService ligueService) {
        super(dao);
        this.mapper = mapper;
        this.gamblerService = gamblerService;
        this.ligueService = ligueService;
    }

    /**
     * Checks whether an event has already been registered with this name
     *
     * @param eventName
     * @return
     */
    public boolean contains(String eventName) {
        List<Event> events = this.getDao().getAll();
        boolean toRet = false;
        if (events != null && events.size() > 0) {
            for (Event event : events) {
                if (event.getName().equals(eventName)) {
                    toRet = true;
                    break;
                }
            }
        }
        return toRet;
    }

    public List<MatchDTO> getMatches(Long id) {
        Event event = this.getDao().findById(id);
        List<Match> ms = ((EventDAO) this.getDao()).getMatches(event);
        List<MatchDTO> matches = new ArrayList<MatchDTO>();
        mapper.getMapper().mapAsCollection(ms, matches, MatchDTO.class);
        return matches;
    }

    public List<GamblerDTO> getGamblers(Long id) {
        Event event = this.getDao().findById(id);
        List<Gambler> gs = ((EventDAO) this.getDao()).getGamblers(event);
        List<GamblerDTO> gamblers = new ArrayList<GamblerDTO>();
        mapper.getMapper().mapAsCollection(gs, gamblers, GamblerDTO.class);
        return gamblers;
    }

    public GamblerDTO getGambler(Long id, String email) {
        Event event = this.getDao().findById(id);
        Gambler g = gamblerService.getGamblerFromEmailAndEvent(email, event);

        return mapper.getMapper().map(g, GamblerDTO.class);
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

    public List<LigueDTO> getLigues(Long id, String email) {
        Event event = this.getFromID(id);
        List<Ligue> ls = ligueService.getLiguesWithMembersFromEvent(event);
        List<LigueDTO> ligues = new ArrayList<LigueDTO>();
        for (Ligue l : ls) {
            LigueDTO newLigue = mapper.getMapper().map(l, LigueDTO.class);
            newLigue.setPoints(ligueService.calculateScore(l));
            newLigue.initialize(email);
            ligues.add(newLigue);
        }

        return ligues;
    }

    public LigueDTO getLigue(Long idEvent, Long idLigue, String email) {
        LigueDTO ligue = mapper.getMapper().map(
                ligueService.getFromKey(KeyBuilder.buildLigueKey(idLigue, idEvent)),
                LigueDTO.class);
        ligue.initialize(email);
        return ligue;
    }

    public Gambler getGamblerFromEventIdAndEmail(Long id, String email) {
        Event event = ((EventDAO) this.getDao()).findById(id);
        return this.gamblerService.getGamblerFromEmailAndEvent(email, event);
    }

    /**
     * Returns the members of the ligue with id ligueId in the event of id eventId
     * @param eventId event's id
     * @param ligueId ligue's id
     * @return members of the ligue (accepted players)
     */
    public List<GamblerDTO> getLigueMembersAndOwner(Long eventId, Long ligueId) {
        
        List<GamblerDTO> members = ligueService.getLigueMembersAndOwner(this.getFromID(eventId), ligueId);

        Collections.sort(members, new Comparator<GamblerDTO>() {
            @Override
            public int compare(GamblerDTO g1, GamblerDTO g2) {
                return g2.getPoints() - g1.getPoints();
            }
        });

        return members;
    }
}
