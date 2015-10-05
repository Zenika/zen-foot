package com.zenika.zenfoot.gae.module;

import com.zenika.zenfoot.gae.dao.*;
import com.zenika.zenfoot.gae.mapper.BetDtoToBetMapper;
import com.zenika.zenfoot.gae.mapper.GamblerDtoToGamblerMapper;
import com.zenika.zenfoot.gae.mapper.MapperFacadeFactory;
import com.zenika.zenfoot.gae.mapper.MatchDtoToMatchMapper;

import javax.inject.Named;


import restx.factory.Module;
import restx.factory.Provides;

import com.google.appengine.api.utils.SystemProperty;
import com.zenika.zenfoot.gae.dao.EventDAO;
import com.zenika.zenfoot.gae.dao.GamblerDAO;
import com.zenika.zenfoot.gae.dao.GamblerDAOImpl;
import com.zenika.zenfoot.gae.dao.MatchDAO;
import com.zenika.zenfoot.gae.dao.MatchDAOImpl;
import com.zenika.zenfoot.gae.dao.PWDLinkDAO;
import com.zenika.zenfoot.gae.dao.PaysDAO;
import com.zenika.zenfoot.gae.dao.SportDAO;
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.mapper.LigueDtoToLigueMapper;
import com.zenika.zenfoot.gae.services.BetService;
import com.zenika.zenfoot.gae.services.EventService;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.LigueService;
import com.zenika.zenfoot.gae.services.MatchService;
import com.zenika.zenfoot.gae.services.SportService;
import com.zenika.zenfoot.gae.services.PWDLinkService;
import com.zenika.zenfoot.gae.services.PaysService;
import com.zenika.zenfoot.gae.services.TeamService;

/**
 * Created by raphael on 24/04/14.
 */
@Module
public class ModelModule {

    //DAOs
    @Provides
    @Named("matchDAO")
    public MatchDAO matchDAO() {
        return new MatchDAOImpl();
    }

    @Provides
    @Named("betDAO")
    public BetDAO betDAO() {
        return new BetDAOImpl();
    }

    //Services
    @Provides
    public BetService betService(@Named("betDAO") BetDAO betDAO) {
        return new BetService(betDAO);
    }

    @Provides
    @Named("matchService")
    public MatchService matchService(MatchDAO matchDAO, MapperFacadeFactory mapperFacadeFactory) {
        MatchService matchService = new MatchService(matchDAO, mapperFacadeFactory);
        return matchService;
    }

    @Provides
    public GamblerDAO gamblerDAO() {
        return new GamblerDAOImpl();
    }

    @Provides
    @Named("gamblerService")
    public GamblerService gamblerService(MatchService matchService, TeamService teamService,
                                         BetService betService, MapperFacadeFactory mapperFacadeFactory,
                                         GamblerDAO gamblerDAO) {
        return new GamblerService(matchService, teamService, mapperFacadeFactory, betService, gamblerDAO);
    }

    @Provides
    public TeamDAO teamDAO() {
        return new TeamDAOImpl();
    }

    @Provides
    public TeamService TeamService(TeamDAO teamDAO) {
        return new TeamService(teamDAO);
    }

    @Provides
    public LigueDAO ligueDAO() {
        return new LigueDAOImpl();
    }

    @Provides
    public LigueService ligueService(TeamService teamService, @Named("gamblerService") GamblerService gamblerService, LigueDAO ligueDAO,
                                     MapperFacadeFactory mapperFacadeFactory) {
        return new LigueService(teamService, gamblerService, ligueDAO, mapperFacadeFactory);
    }

    @Provides
    public PWDLinkDAO pWDLinkDAO() {
        return new PWDLinkDAOImpl();
    }

    @Provides
    public EventDAO eventDAO() {
        return new EventDAOImpl();
    }

    @Provides
    @Named("gamblerServiceWithoutMapper")
    public GamblerService gamblerService(@Named("gamblerDAO") GamblerDAO gamblerDAO) {
        return new GamblerService(null, null, null, null, gamblerDAO);
    }

    @Provides
    @Named("eventService")
    public EventService eventService(EventDAO eventDAO, MapperFacadeFactory mapperFacadeFactory,
                                     @Named("gamblerService") GamblerService gamblerService, LigueService ligueService) {
        return new EventService(eventDAO, mapperFacadeFactory, gamblerService, ligueService);
    }

    @Provides
    public PWDLinkService pWDLinkService(PWDLinkDAO pWDLinkDAO) {
        return new PWDLinkService(pWDLinkDAO);
    }

    //DAOs
    @Provides
    @Named("sportDAO")
    public SportDAO sportDAO() {
        return new SportDAO();
    }

    @Provides
    @Named("sportService")
    public SportService sportService(@Named("sportDAO") SportDAO sportDAO) {
        return new SportService(sportDAO);
    }

    //DAOs
    @Provides
    @Named("paysDAO")
    public PaysDAO paysDAO() {
        return new PaysDAOImpl();
    }

    @Provides
    @Named("paysService")
    public PaysService paysService(@Named("paysDAO") PaysDAO paysDAO) {
        return new PaysService(paysDAO);
    }
}
