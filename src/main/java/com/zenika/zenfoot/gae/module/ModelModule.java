package com.zenika.zenfoot.gae.module;

import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.repackaged.org.joda.time.DateTimeZone;
import com.zenika.zenfoot.gae.dao.*;
import com.zenika.zenfoot.gae.model.Match;
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
    @Named("matchRepoGAE")
    public MatchRepository matchRepositoryGAE(@Named("matchDAO") MatchDAO matchDAO) {
        MatchRepository matchRepository = new MatchRepository(matchDAO);

        if(SystemProperty.environment.value()==SystemProperty.Environment.Value.Development){
            Match[] matches = GenerateMatches.generate();
            List<Match> registered = matchRepository.getAll();

            //check whether there were registered matchs
            if (registered.size() == 0) {
                for (int i = 0; i < matches.length; i++) {
                    //TODO ONLY FOR TESTS
                    Match match = matches[i];
                    match.setDate(DateTime.now().plusSeconds(30 * i));
                    if(i>30){
                        match.setDate(DateTime.now().minusDays(i).withHourOfDay(i%23));
                    }
                    matchRepository.createUpdate(match);

                }

            }
        }


        return matchRepository;
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
    public MatchService matchService(@Named("matchRepoGAE") MatchRepository matchRepository) {

        MatchService matchService = new MatchService(matchRepository);
/*
        Participant participant1=new Participant().setGroupe(Groupe.G).setPays("Corée du Nord");
        Participant participant2 = new Participant().setGroupe(Groupe.G).setPays("Thaïlande");
        Match match = new Match().setDate(DateTime.now().plusMinutes(2)).setParticipant1(participant1).setParticipant2(participant2);
        matchRepository.createUpdate(match);*/

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
    public GamblerService gamblerService(GamblerRepository gamblerRepository, MatchService matchService, TeamDAO teamDAO, RankingDAO rankingDAO) {
        return new GamblerService(gamblerRepository, matchService, teamDAO, rankingDAO);
    }

    @Provides
    public TeamDAO teamDAO() {
        return new TeamDAO();
    }

    @Provides
    public RankingDAO rankingDAO() {
        return new RankingDAO();
    }

    @Provides
    public LigueService ligueService(TeamDAO teamDAO, GamblerDAO gamblerDAO, RankingDAO rankingDAO){
        return new LigueService(teamDAO, gamblerDAO, rankingDAO);
    }
}
