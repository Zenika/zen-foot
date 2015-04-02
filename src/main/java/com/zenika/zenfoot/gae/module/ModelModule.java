package com.zenika.zenfoot.gae.module;

import java.util.List;

import javax.inject.Named;

import org.joda.time.DateTime;

import restx.factory.Module;
import restx.factory.Provides;

import com.google.appengine.api.utils.SystemProperty;
import com.zenika.zenfoot.gae.common.SportEnum;
import com.zenika.zenfoot.gae.dao.EventDAO;
import com.zenika.zenfoot.gae.dao.GamblerDAO;
import com.zenika.zenfoot.gae.dao.GamblerDAOImpl;
import com.zenika.zenfoot.gae.dao.GamblerRankingDAO;
import com.zenika.zenfoot.gae.dao.MatchDAO;
import com.zenika.zenfoot.gae.dao.MatchDAOImpl;
import com.zenika.zenfoot.gae.dao.PWDLinkDAO;
import com.zenika.zenfoot.gae.dao.PaysDAO;
import com.zenika.zenfoot.gae.dao.SportDAO;
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.dao.TeamRankingDAO;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.model.Sport;
import com.zenika.zenfoot.gae.services.BetRepository;
import com.zenika.zenfoot.gae.services.BetService;
import com.zenika.zenfoot.gae.services.EventService;
import com.zenika.zenfoot.gae.services.GamblerRankingService;
import com.zenika.zenfoot.gae.services.GamblerRepository;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.LigueService;
import com.zenika.zenfoot.gae.services.MatchService;
import com.zenika.zenfoot.gae.services.PaysService2;
import com.zenika.zenfoot.gae.services.SportService;
import com.zenika.zenfoot.gae.services.TeamRankingService;

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
            Match[] matches = GenerateMatches.generate();
            List<Match> registered = matchDAO.getAll();

            //check whether there were registered matchs
            if (registered.size() == 0) {
                for (int i = 0; i < matches.length; i++) {
                    //TODO ONLY FOR TESTS
                    Match match = matches[i];
                    match.setSport(new Sport( SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport()));
                    match.setDate(DateTime.now().plusSeconds(30 * i));
                    if(i>30){
                        match.setDate(DateTime.now().minusDays(i).withHourOfDay(i%23));
                    }
                    matchDAO.createUpdate(match);

                }

            }
        }


        return matchDAO;
    }

    //DAOs
    @Provides
    @Named("matchDAO")
    public MatchDAO matchDAO() {
        return new MatchDAOImpl();
    }

    //Services

    @Provides
    @Named("betrepo")
    public BetRepository betRepository() {
        return new BetRepository();
    }


    @Provides
    @Named("betservice")
    public BetService betService(@Named("betrepo") BetRepository betRepository) {
        return new BetService(betRepository);
    }

    @Provides
    @Named("matchService")
    public MatchService matchService(@Named("matchDAOMock") MatchDAO matchDAO) {
        MatchService matchService = new MatchService(matchDAO);
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
    public GamblerService gamblerService(GamblerRepository gamblerRepository, MatchService matchService, TeamDAO teamDAO, GamblerRankingDAO rankingDAO, TeamRankingDAO teamRankingDAO) {
        return new GamblerService(gamblerRepository, matchService, teamDAO, rankingDAO, teamRankingDAO);
    }

    @Provides
    public TeamDAO teamDAO() {
        return new TeamDAO();
    }

    @Provides
    public GamblerRankingDAO rankingDAO() {
        return new GamblerRankingDAO();
    }

    @Provides
    public TeamRankingDAO teamRankingDAO(){
        return new TeamRankingDAO();
    }

    @Provides
    public LigueService ligueService(TeamDAO teamDAO, GamblerDAO gamblerDAO, GamblerRankingDAO rankingDAO, TeamRankingDAO teamRankingDAO){
        return new LigueService(teamDAO, gamblerDAO, rankingDAO, teamRankingDAO);
    }

    @Provides
    public PWDLinkDAO pwdLinkDAO(){
        return new PWDLinkDAO();
    }

    @Provides

    public GamblerRankingService gamblerRankingService(GamblerRankingDAO gamblerRankingDAO){
        return new GamblerRankingService(gamblerRankingDAO);
    }

    @Provides
    public TeamRankingService teamRankingService(TeamRankingDAO teamRankingDAO){
        return new TeamRankingService(teamRankingDAO);
    }

    @Provides
    public EventDAO eventDAO(){
        return new EventDAO();
    }

    @Provides
    public EventService eventService(EventDAO eventDAO){
        return new EventService(eventDAO);
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
    
    
    //DAOs
    @Provides
    @Named("sportDAO")
    public SportDAO sportDAO() {
        return new SportDAO();
    }	 
	
    @Provides
    @Named("sportService")
    public SportService sportService(@Named("sportDAO") SportDAO sportDAO){
        return new SportService(sportDAO);
    }

    //DAOs
    @Provides
    @Named("paysDAO")
    public PaysDAO paysDAO() {
        return new PaysDAO();
    }	 
	
    @Provides
    @Named("paysService")
    public PaysService2 paysService(@Named("paysDAO") PaysDAO paysDAO){
        return new PaysService2(paysDAO);
    }
}
