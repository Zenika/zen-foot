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
            matchRepository.createUpdate(match);
        }

        Participant participant1=new Participant().setGroupe(Groupe.G).setPays("Corée du Nord");
        Participant participant2 = new Participant().setGroupe(Groupe.G).setPays("Thaïlande");
        Match match = new Match().setDate(DateTime.now()).setParticipant1(participant1).setParticipant2(participant2);
        matchRepository.createUpdate(match);
        return matchRepository;
    }

    @Provides
    @Named("matchRepoGAE")
    public MatchRepository matchRepositoryGAE(@Named("matchDAO") MatchDAO matchDAO) {
        MatchRepository matchRepository = new MatchRepository(matchDAO);



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
    public MatchService matchService(@Named("matchRepoDev") MatchRepository matchRepository) {

        MatchService matchService = new MatchService(matchRepository);

        Participant participant1=new Participant().setGroupe(Groupe.G).setPays("Corée du Nord");
        Participant participant2 = new Participant().setGroupe(Groupe.G).setPays("Thaïlande");
        Match match = new Match().setDate(DateTime.now().plusMinutes(2)).setParticipant1(participant1).setParticipant2(participant2);
        matchRepository.createUpdate(match);

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
