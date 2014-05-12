package com.zenika.zenfoot.gae.module;

import com.zenika.zenfoot.gae.dao.GamblerDAO;
import com.zenika.zenfoot.gae.dao.GamblerDAOImpl;
import com.zenika.zenfoot.gae.dao.MatchDAO;
import com.zenika.zenfoot.gae.dao.MatchDAOImpl;
import com.zenika.zenfoot.gae.model.Groupe;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.model.Participant;
import com.zenika.zenfoot.gae.services.*;
import org.joda.time.DateTime;
import restx.factory.Module;
import restx.factory.Provides;

import javax.inject.Named;
import java.util.ArrayList;

/**
 * Created by raphael on 24/04/14.
 */
@Module
public class ModelModule {


    @Provides
    @Named("matchRepoDev")
    public MatchRepository matchRepository(@Named("matchDAO") MatchDAO matchDAO) {
        MatchRepository matchRepository = new MatchRepository(matchDAO);
        ArrayList<Match> matchs = GenerateMatches.generate();
        for (Match match : matchs) {
            matchRepository.createMatch2(match);
        }
        return matchRepository;
    }

    @Provides
    @Named("matchRepoGAE")
    public MatchRepository matchRepositoryGAE(@Named("matchDAO") MatchDAO matchDAO) {
        MatchRepository matchRepository = new MatchRepository(matchDAO);
        matchRepository.createMatch2(new Match().setDate(DateTime.now().plusMinutes(2))
                .setParticipant1(new Participant().setGroupe(Groupe.A).setPays("Azerbaïjan"))
                .setParticipant2(new Participant().setGroupe(Groupe.A).setPays("Corée du Nord")));

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
    public GamblerService gamblerService(GamblerRepository gamblerRepository, MatchService matchService) {
        return new GamblerService(gamblerRepository, matchService);
    }
}