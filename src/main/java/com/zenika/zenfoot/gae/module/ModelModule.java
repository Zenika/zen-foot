package com.zenika.zenfoot.gae.module;

import com.zenika.zenfoot.gae.dao.GamblerDAO;
import com.zenika.zenfoot.gae.dao.GamblerDAOImpl;
import com.zenika.zenfoot.gae.dao.MatchDAO;
import com.zenika.zenfoot.gae.dao.MatchDAOImpl;
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
    @Named("matchRepo")
    public MatchRepository matchRepository(@Named("matchDAO") MatchDAO matchDAO) {
        return new MatchRepository(matchDAO);
    }


    //DAOs
    @Provides
    @Named("matchDAO")
    public MatchDAO matchDAO(){
        return new MatchDAOImpl();
    }

    //Services

    @Provides
    @Named("betrepo")
    public BetRepository betRepository(){
        return new BetRepository();
    }


    @Provides
    @Named("betservice")
    public BetService betService(@Named("betrepo")BetRepository betRepository){
        return new BetService(betRepository);
    }

    @Provides
    @Named("matchService")
    public MatchService matchService(MatchRepository matchRepository) {

        MatchService matchService = new MatchService(matchRepository);

        return matchService;

    }

    @Provides
    public GamblerDAO gamblerDAO(){
        return new GamblerDAOImpl();
    }

    @Provides
    public GamblerRepository gamblerRepository(GamblerDAO gamblerDAO){
        return new GamblerRepository(gamblerDAO);
    }

    @Provides
    public GamblerService gamblerService(GamblerRepository gamblerRepository){
        return new GamblerService(gamblerRepository);
    }
}
