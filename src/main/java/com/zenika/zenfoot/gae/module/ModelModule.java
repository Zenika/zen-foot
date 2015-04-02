package com.zenika.zenfoot.gae.module;

import com.google.appengine.api.utils.SystemProperty;
import com.zenika.zenfoot.gae.dao.*;
import com.zenika.zenfoot.gae.mapper.BetDtoToBetMapper;
import com.zenika.zenfoot.gae.mapper.GamblerDtoToGamblerMapper;
import com.zenika.zenfoot.gae.mapper.MapperFacadeFactory;
import com.zenika.zenfoot.gae.mapper.MatchDtoToMatchMapper;
import com.zenika.zenfoot.gae.services.*;
import restx.factory.Module;
import restx.factory.Provides;

import javax.inject.Named;

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
    public GamblerRepository gamblerRepository(GamblerDAO gamblerDAO) {
        return new GamblerRepository(gamblerDAO);
    }

    @Provides
    public GamblerService gamblerService(GamblerRepository gamblerRepository, MatchService matchService, TeamDAO teamDAO, 
            TeamRankingDAO teamRankingDAO, BetService betService, @Named("mapperFacadeFactory") MapperFacadeFactory mapperFacadeFactory,
            GamblerDAO gamblerDAO) {
        return new GamblerService(gamblerRepository, matchService, teamDAO, teamRankingDAO, mapperFacadeFactory, betService, gamblerDAO);
    }

    @Provides
    public TeamDAO teamDAO() {
        return new TeamDAO();
    }

    @Provides
    public TeamRankingDAO teamRankingDAO(){
        return new TeamRankingDAO();
    }

    @Provides
    public LigueService ligueService(TeamDAO teamDAO, GamblerDAO gamblerDAO, TeamRankingDAO teamRankingDAO){
        return new LigueService(teamDAO, gamblerDAO, teamRankingDAO);
    }

    @Provides
    public PWDLinkDAO pwdLinkDAO(){
        return new PWDLinkDAO();
    }

    @Provides
    public TeamRankingService teamRankingService(TeamRankingDAO teamRankingDAO){
        return new TeamRankingService(teamRankingDAO);
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
    public EventService eventService(EventDAO eventDAO, @Named("mapperFacadeFactory") MapperFacadeFactory mapperFacadeFactory, GamblerDAO gamblerDAO){
        return new EventService(eventDAO, mapperFacadeFactory, gamblerDAO);
    }
}
