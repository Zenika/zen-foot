package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.impl.translate.opt.joda.JodaTimeTranslators;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.model.Country;
import com.zenika.zenfoot.gae.model.Sport;
import com.zenika.zenfoot.gae.model.Team;
import com.zenika.zenfoot.gae.model.TeamRanking;
import com.zenika.zenfoot.gae.utils.PWDLink;
import com.zenika.zenfoot.user.User;

/**
 * Created by raphael on 23/04/14.
 */
public class OfyService {

    static {
        factory().register(User.class);
        factory().register(Match.class);
        factory().register(Gambler.class);
        factory().register(Team.class);
        factory().register(TeamRanking.class);
        factory().register(PWDLink.class);
        factory().register(Event.class);
        factory().register(Bet.class);
        factory().register(Country.class);
        factory().register(Sport.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        ObjectifyFactory objectifyFactory = ObjectifyService.factory();
        JodaTimeTranslators.add(objectifyFactory);
        return objectifyFactory;
    }
}
