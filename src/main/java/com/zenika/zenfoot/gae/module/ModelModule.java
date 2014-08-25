package com.zenika.zenfoot.gae.module;

import com.google.appengine.api.utils.SystemProperty;
import com.zenika.zenfoot.gae.dao.*;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.model.TeamRanking;
import com.zenika.zenfoot.gae.services.*;
import org.joda.time.DateTime;
import restx.factory.Module;
import restx.factory.Provides;

import javax.inject.Named;
import java.util.List;

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

}
