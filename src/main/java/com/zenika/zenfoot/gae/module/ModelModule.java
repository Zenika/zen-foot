package com.zenika.zenfoot.gae.module;

import com.zenika.zenfoot.gae.dao.*;
import com.zenika.zenfoot.gae.mapper.MapperFacadeFactory;

import javax.inject.Named;


import restx.factory.Module;
import restx.factory.Provides;

import com.zenika.zenfoot.gae.dao.EventDAO;
import com.zenika.zenfoot.gae.dao.GamblerDAO;
import com.zenika.zenfoot.gae.dao.GamblerDAOImpl;
import com.zenika.zenfoot.gae.dao.MatchDAO;
import com.zenika.zenfoot.gae.dao.MatchDAOImpl;
import com.zenika.zenfoot.gae.dao.PWDLinkDAO;
import com.zenika.zenfoot.gae.dao.CountryDAO;
import com.zenika.zenfoot.gae.dao.SportDAO;
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.services.BetService;
import com.zenika.zenfoot.gae.services.EventService;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.LigueService;
import com.zenika.zenfoot.gae.services.MatchService;
import com.zenika.zenfoot.gae.services.SportService;
import com.zenika.zenfoot.gae.services.PWDLinkService;
import com.zenika.zenfoot.gae.services.CountryService;
import com.zenika.zenfoot.gae.services.TeamRankingService;
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
    public GamblerService gamblerService(MatchService matchService, LigueService ligueService,
                                         BetService betService, MapperFacadeFactory mapperFacadeFactory,
                                         GamblerDAO gamblerDAO) {
        return new GamblerService(matchService, ligueService, mapperFacadeFactory, betService, gamblerDAO);
    }

    @Provides
    public TeamDAO teamDAO() {
        return new TeamDAOImpl();
    }

    @Provides
    public LigueDAO ligueDAO() {
        return new LigueDAOImpl();
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

//    @Provides
//    @Named("genericPaysDAO")
//    public GenericDAO<Country> genericPaysDAO(){
//        return new GenericDAO<Country>(Country.class);
//    }
//    
//    @Provides
//    @Named("countryService")
//    public CountryService countryService(){
//    	return new CountryService(CountryService.class,  genericPaysDAO());
//    }
//    
//    @Provides
//    @Named("genericSportDAO")
//    public GenericDAO<Sport> genericSportDAO(){
//        return new GenericDAO<Sport>(Sport.class);
//    }
//    
//    @Provides
//    @Named("sportService")
//    public SportService sportService(){
//    	return new SportService(SportService.class,  genericSportDAO());
//    }

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
    @Named("countryDAO")
    public CountryDAO countryDAO() {
        return new CountryDAOImpl();
    }	 
	
    @Provides
    @Named("countryService")
    public CountryService countryService(@Named("countryDAO") CountryDAO countryDAO){
        return new CountryService(countryDAO);
    }
}
