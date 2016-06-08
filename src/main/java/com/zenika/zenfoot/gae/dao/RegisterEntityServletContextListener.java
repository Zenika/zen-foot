package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.impl.translate.opt.joda.JodaTimeTranslators;
import com.zenika.zenfoot.gae.model.*;
import com.zenika.zenfoot.gae.utils.PWDLink;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by armel on 09/10/15.
 */
public class RegisterEntityServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ObjectifyFactory objectifyFactory = ObjectifyService.factory();
        JodaTimeTranslators.add(objectifyFactory);
        objectifyFactory.register(User.class);
        objectifyFactory.register(Match.class);
        objectifyFactory.register(Gambler.class);
        objectifyFactory.register(Ligue.class);
        objectifyFactory.register(PWDLink.class);
        objectifyFactory.register(Event.class);
        objectifyFactory.register(Bet.class);
        objectifyFactory.register(Country.class);
        objectifyFactory.register(Sport.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //nothing to do
    }
}
