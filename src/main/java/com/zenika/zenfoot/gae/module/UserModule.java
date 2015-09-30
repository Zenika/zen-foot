package com.zenika.zenfoot.gae.module;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.dao.MatchDAO;
import com.zenika.zenfoot.gae.dao.MatchDAOImpl;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.services.*;


import restx.admin.AdminModule;
import restx.factory.Provides;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import com.google.appengine.api.utils.SystemProperty;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.model.Pays;
import com.zenika.zenfoot.gae.model.Sport;
import com.zenika.zenfoot.gae.services.SportService;
import com.zenika.zenfoot.gae.dao.UserDAO;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.MatchService;
import com.zenika.zenfoot.gae.services.ZenfootUserService;
import com.zenika.zenfoot.gae.model.User;

import javax.inject.Named;

import restx.factory.Module;
import restx.security.UserService;


@Module
public class UserModule {

    @Provides
    @Named("zenfootUserService")
    public ZenfootUserService zenfootUserService(UserDAO userDAO) {
        return new ZenfootUserService(userDAO);
    }

    @Provides
    @Named("userServiceGAE")
    public UserService getUserService2(@Named("zenfootUserService") ZenfootUserService zenfootUserService, @Named("gamblerService") GamblerService gamblerService,
                                       MatchService matchService, EventService eventService, @Named("paysService") PaysService paysService, @Named("sportService") SportService sportService) {

        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
            User admin = new User().setName("admin").setPrenom("admin").setEmail(
                    "admin@zenika.com").setRoles(Arrays.asList(Roles.ADMIN, AdminModule.RESTX_ADMIN_ROLE));

            //raphael.setLastUpdated(DateTime.now());
            admin.setPassword("2205");

            User jean = new User().setName("Bon").setPrenom("Jean").setEmail("jean.bon@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            jean.setPassword("999");

            User mira = new User().setName("Sorvino").setPrenom("Mira").setEmail("j1@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            mira.setPassword("999");

            User bill = new User().setName("Murray").setPrenom("Bill").setEmail("j2@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            bill.setPassword("999");

            User andy = new User().setPrenom("Andy").setName("Mc-Dowell").setEmail("j3@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            andy.setPassword("999");

            User sophie = new User().setPrenom("Sophie").setName("Marceau").setEmail("j4@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            sophie.setPassword("999");

            User kate = new User().setPrenom("Kate").setName("Winslet").setEmail("j5@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            kate.setPassword("999");

            User olivier = new User().setPrenom("Olivier").setName("Martinez").setEmail("j6@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            olivier.setPassword("999");

            User russell = new User().setPrenom("Russell").setName("Crowe").setEmail("j7@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            russell.setPassword("999");

            User harold = new User().setPrenom("Harold").setName("Ramis").setEmail("j8@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            harold.setPassword("999");

            User richard = new User().setPrenom("Richard").setName("Virenque").setEmail("j9@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            richard.setPassword("999");

            User jc = new User().setPrenom("Jean-Claude").setName("Duss").setEmail("j10@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            jc.setPassword("999");

            User leonardo = new User().setPrenom("Leonardo").setName("Di-Caprio").setEmail("j11@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            leonardo.setPassword("999");

            User j = new User().setEmail("j@j.fr").setName("j").setPrenom("j").setRoles(Arrays.asList(Roles.GAMBLER));
            j.setPassword("999");

            User l = new User().setEmail("l@l.fr").setName("l").setPrenom("l").setRoles(Arrays.asList(Roles.GAMBLER));
            l.setPassword("999");

            User k = new User().setEmail("k@k.fr").setName("k").setPrenom("k").setRoles(Arrays.asList(Roles.GAMBLER));
            k.setPassword("999");

            zenfootUserService.createOrUpdate(admin);
            zenfootUserService.createOrUpdate(jean);
            zenfootUserService.createOrUpdate(mira);
            zenfootUserService.createOrUpdate(bill);
            zenfootUserService.createOrUpdate(andy);
            zenfootUserService.createOrUpdate(sophie);
            zenfootUserService.createOrUpdate(kate);
            zenfootUserService.createOrUpdate(olivier);
            zenfootUserService.createOrUpdate(russell);
            zenfootUserService.createOrUpdate(harold);
            zenfootUserService.createOrUpdate(richard);
            zenfootUserService.createOrUpdate(jc);
            zenfootUserService.createOrUpdate(leonardo);
            zenfootUserService.createOrUpdate(j);
            zenfootUserService.createOrUpdate(k);
            zenfootUserService.createOrUpdate(l);


            Event e = new Event();
            e.setName("Cdm 2014 Foot");
            e.setStart(DateTime.now().minusDays(5).toDate());
            e.setEnd(DateTime.now().plusDays(5).toDate());
            eventService.createOrUpdate(e);

            injectedPays(paysService);
            injectedSport(sportService);

            Event e2 = new Event();
            e2.setName("Cdm 2015 Rugby");
            e2.setStart(DateTime.now().plusDays(5).toDate());
            e2.setEnd(DateTime.now().plusDays(15).toDate());
            eventService.createOrUpdate(e2);
//            MatchDAO matchDAO = new MatchDAOImpl();

            Match[] matches = GenerateMatches.generate();
            List<Match> registered = matchService.getAll();

            //check whether there were registered matchs
//                if (registered.size() == 0) {
            for (int i = 0; i < matches.length; i++) {
                //TODO ONLY FOR TESTS
                Match match = matches[i];
                match.setDate(DateTime.now().plusSeconds(30 * i));
                if (i > 30) {
                    match.setDate(DateTime.now().minusDays(i).withHourOfDay(i % 23));
                }
                match.setEvent(Key.create(Event.class, e2.getId()));
//                        matchDAO.createUpdate(match);
                matchService.createOrUpdate(match);
            }

//                }

        }
        return zenfootUserService;
    }


    /**
     * injection d'une liste de sport
     *
     * @param sportService
     */
    private void injectedSport(SportService sportService) {
        // le modele
        Sport sport = new Sport();
        sport.setName("Foot");
//    	sport.setId((long)1);

        sportService.createOrUpdate(sport);

        sport = new Sport();
        sport.setName("Rugby");
//		sport.setId((long) 2);

        sportService.createOrUpdate(sport);

        sport = new Sport();
        sport.setName("HandBall");
//		sport.setId((long) 3);

        sportService.createOrUpdate(sport);

        sport = new Sport();
        sport.setName("Basket");
//		sport.setId((long) 4);
        sportService.createOrUpdate(sport);

        sport = new Sport();
        sport.setName("Tennis");
//		sport.setId((long) 5);
        sportService.createOrUpdate(sport);
    }


    /**
     * injection d'une liste de pays
     *
     * @param paysService
     */
    private void injectedPays(PaysService paysService) {
        Pays p = new Pays();
        // p.setIdPays(idPays);
//		p.setIdPays((long)1);
        p.setNomPays("FRANCE");
        paysService.createOrUpdate(p);
    }

    @Provides
    @Named("userService")
    public UserService getUserService(@Named("userServiceGAE") UserService userService) {
        return userService;
    }
}
