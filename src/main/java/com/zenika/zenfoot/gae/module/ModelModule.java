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
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.dao.TeamRankingDAO;
import com.zenika.zenfoot.gae.services.BetService;
import com.zenika.zenfoot.gae.services.EventService;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.LigueService;
import com.zenika.zenfoot.gae.services.MatchService;
import com.zenika.zenfoot.gae.services.PWDLinkService;
import com.zenika.zenfoot.gae.services.TeamRankingService;
import com.zenika.zenfoot.gae.services.TeamService;

/**
 * Created by raphael on 24/04/14.
 */
@Module
public class ModelModule {


    @Provides
    @Named("matchDAOMock")
    public MatchDAO matchDAO2() {

        MatchDAO matchDAO = new MatchDAOImpl();

        if(SystemProperty.environment.value()==SystemProperty.Environment.Value.Development){
            /*Match[] matches = GenerateMatches.generate();
            List<Match> registered = matchDAO.getAll();

            //check whether there were registered matchs
            if (registered.size() == 0) {
                for (int i = 0; i < matches.length; i++) {
                    //TODO ONLY FOR TESTS
                    Match match = matches[i];
                    match.setSport(new Sport( (long) i+1, "Foot"));
                    match.setDate(DateTime.now().plusSeconds(30 * i));
                    if(i>30){
                        match.setDate(DateTime.now().minusDays(i).withHourOfDay(i%23));
                    }
                    matchDAO.createUpdate(match);

                }

            }*/
        }


        return matchDAO;
    }

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
    public MatchService matchService(@Named("matchDAOMock") MatchDAO matchDAO, @Named("mapperFacadeFactory") MapperFacadeFactory mapperFacadeFactory) {
        MatchService matchService = new MatchService(matchDAO, mapperFacadeFactory);
        return matchService;
    }

    @Provides
    public GamblerDAO gamblerDAO() {
        return new GamblerDAOImpl();
    }

    @Provides
    public GamblerService gamblerService(MatchService matchService, TeamService teamService, 
            TeamRankingService teamRankingService, BetService betService, @Named("mapperFacadeFactory") MapperFacadeFactory mapperFacadeFactory,
            GamblerDAO gamblerDAO) {
        return new GamblerService(matchService, teamService, teamRankingService, mapperFacadeFactory, betService, gamblerDAO);
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
    public TeamRankingDAO teamRankingDAO(){
        return new TeamRankingDAOImpl();
    }

    @Provides
    public TeamRankingService teamRankingService(TeamRankingDAO teamRankingDAO) {
        return new TeamRankingService(teamRankingDAO);
    }

    @Provides
    public LigueService ligueService(TeamService teamService, GamblerService gamblerService, TeamRankingService teamRankingService){
        return new LigueService(teamService, gamblerService, teamRankingService);
    }

    @Provides
    public PWDLinkDAO pWDLinkDAO(){
        return new PWDLinkDAOImpl();
    }

    @Provides
    public EventDAO eventDAO(){
        return new EventDAOImpl();
    }
    
    @Provides
    @Named("matchDtoToMatchMapper")
    public MatchDtoToMatchMapper matchDtoToMatchMapper(){
        return new MatchDtoToMatchMapper();
    }
    @Provides
    @Named("gamblerDtoToGamblerMapper")
    public GamblerDtoToGamblerMapper gamblerDtoToGamblerMapper(){
        return new GamblerDtoToGamblerMapper();
    }
    @Provides
    @Named("betDtoToBetMapper")
    public BetDtoToBetMapper betDtoToBetMapper(){
        return new BetDtoToBetMapper();
    }
    
    @Provides
    @Named("mapperFacadeFactory")
    public MapperFacadeFactory mapperFacadeFactory(@Named("matchDtoToMatchMapper") MatchDtoToMatchMapper matchDtoToMatchMapper, 
            GamblerDtoToGamblerMapper gamblerDtoToGamblerMapper, BetDtoToBetMapper betDtoToBetMapper){
        return new MapperFacadeFactory(matchDtoToMatchMapper, gamblerDtoToGamblerMapper, betDtoToBetMapper);
    }

    @Provides
    @Named("eventService")
    public EventService eventService(EventDAO eventDAO, @Named("mapperFacadeFactory") MapperFacadeFactory mapperFacadeFactory, GamblerService 
            gamblerService){
        return new EventService(eventDAO, mapperFacadeFactory, gamblerService);
    }
    
    @Provides
    public PWDLinkService pWDLinkService(PWDLinkDAO pWDLinkDAO) {
        return new PWDLinkService(pWDLinkDAO);
    }
    
//    @Provides
//    @Named("genericPaysDAO")
//    public GenericDAO<Pays> genericPaysDAO(){
//        return new GenericDAO<Pays>(Pays.class);
//    }
//    
//    @Provides
//    @Named("paysService")
//    public PaysService paysService(){
//    	return new PaysService(PaysService.class,  genericPaysDAO());
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

    
}
