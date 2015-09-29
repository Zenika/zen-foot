package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.dao.EventDAO;
import com.zenika.zenfoot.gae.dto.BetDTO;
import com.zenika.zenfoot.gae.dto.GamblerDTO;
import com.zenika.zenfoot.gae.dto.LigueDTO;
import com.zenika.zenfoot.gae.dto.MatchDTO;
import com.zenika.zenfoot.gae.mapper.MapperFacadeFactory;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Ligue;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.utils.KeyBuilder;

import java.util.ArrayList;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by raphael on 28/08/14.
 */
public class EventService extends AbstractGenericService<Event> {

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
}
