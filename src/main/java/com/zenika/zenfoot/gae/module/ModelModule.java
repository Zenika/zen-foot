package com.zenika.zenfoot.gae.module;

import com.zenika.zenfoot.gae.dao.MatchDAO;
import com.zenika.zenfoot.gae.dao.MatchDAOImpl;
import com.zenika.zenfoot.gae.services.MatchRepository;
import com.zenika.zenfoot.gae.services.MatchService;
import restx.factory.Module;
import restx.factory.Provides;

import javax.inject.Named;

/**
 * Created by raphael on 24/04/14.
 */
@Module
public class ModelModule {

    @Provides
    @Named("matchService")
    public MatchService matchService(MatchRepository matchRepository) {

        MatchService matchService = new MatchService(matchRepository);

        return matchService;

    }

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
}
