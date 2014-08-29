package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.impl.translate.opt.joda.JodaTimeTranslators;
import com.zenika.zenfoot.gae.model.*;
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
        factory().register(GamblerRanking.class);
        factory().register(PWDLink.class);
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
