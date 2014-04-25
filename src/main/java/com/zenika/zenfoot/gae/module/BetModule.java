package com.zenika.zenfoot.gae.module;

import com.zenika.zenfoot.gae.model.BetMatch;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.model.Pays;
import com.zenika.zenfoot.gae.services.BetRepository;
import com.zenika.zenfoot.gae.services.SessionInfo;
import com.zenika.zenfoot.user.User;
import org.joda.time.DateTime;
import restx.factory.Module;
import restx.factory.Provides;

import javax.inject.Named;

/**
 * Created by raphael on 24/04/14.
 */
@Module
public class BetModule {

    @Provides
    @Named("betrepository")
    public BetRepository getRepositories(@Named("sessioninfo")SessionInfo sessionInfo) {

        User user = new User().setEmail("raphael.martignoni@zenika.com").setName("raphael");
        for(int i=0;i<10;i++){
            System.out.printf("ICIICICICICICICICICICICICICICI");
        }

        Pays bresil = new Pays().setName("Bresil");
        Pays croatie = new Pays().setName("Croatie");
        Match match = new Match(new DateTime(2014, 6, 22, 22, 0), bresil, croatie);
        BetMatch betMatch = new BetMatch(match);

        BetRepository betRepository = new BetRepository();
        betRepository.addBet(user, betMatch);
        return betRepository;
    }
}
