package com.zenika.zenfoot.gae.module;

import com.googlecode.objectify.Key;
import com.neovisionaries.i18n.CountryCode;
import com.zenika.zenfoot.gae.mapper.MapperFacadeFactory;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.services.*;


import restx.admin.AdminModule;
import restx.factory.Provides;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import com.google.appengine.api.utils.SystemProperty;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.model.Country;
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
    public ZenfootUserService zenfootUserService(UserDAO userDAO, MapperFacadeFactory mapperFacadeFactory) {
        return new ZenfootUserService(userDAO, mapperFacadeFactory);
    }

    @Provides
    @Named("userServiceGAE")
    public UserService getUserService2(@Named("zenfootUserService") ZenfootUserService zenfootUserService, @Named("gamblerService") GamblerService gamblerService,
                                       MatchService matchService, EventService eventService, @Named("countryService") CountryService countryService, @Named("sportService") SportService sportService) {

        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
            User admin = new User().setLastname("admin").setFirstname("admin").setEmail(
                    "admin@zenika.com").setRoles(Arrays.asList(Roles.ADMIN, AdminModule.RESTX_ADMIN_ROLE));

            //raphael.setLastUpdated(DateTime.now());
            admin.hashAndSetPassword("2205");

            User jean = new User().setLastname("Bon").setFirstname("Jean").setEmail("jean.bon@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            jean.hashAndSetPassword("999");

            User mira = new User().setLastname("Sorvino").setFirstname("Mira").setEmail("j1@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            mira.hashAndSetPassword("999");

            User bill = new User().setLastname("Murray").setFirstname("Bill").setEmail("j2@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            bill.hashAndSetPassword("999");

            User andy = new User().setFirstname("Andy").setLastname("Mc-Dowell").setEmail("j3@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            andy.hashAndSetPassword("999");

            User sophie = new User().setFirstname("Sophie").setLastname("Marceau").setEmail("j4@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            sophie.hashAndSetPassword("999");

            User kate = new User().setFirstname("Kate").setLastname("Winslet").setEmail("j5@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            kate.hashAndSetPassword("999");

            User olivier = new User().setFirstname("Olivier").setLastname("Martinez").setEmail("j6@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            olivier.hashAndSetPassword("999");

            User russell = new User().setFirstname("Russell").setLastname("Crowe").setEmail("j7@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            russell.hashAndSetPassword("999");

            User harold = new User().setFirstname("Harold").setLastname("Ramis").setEmail("j8@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            harold.hashAndSetPassword("999");

            User richard = new User().setFirstname("Richard").setLastname("Virenque").setEmail("j9@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            richard.hashAndSetPassword("999");

            User jc = new User().setFirstname("Jean-Claude").setLastname("Duss").setEmail("j10@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            jc.hashAndSetPassword("999");

            User leonardo = new User().setFirstname("Leonardo").setLastname("Di-Caprio").setEmail("j11@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
            leonardo.hashAndSetPassword("999");

            User j = new User().setEmail("j@j.fr").setLastname("j").setFirstname("j").setRoles(Arrays.asList(Roles.GAMBLER));
            j.hashAndSetPassword("999");

            User l = new User().setEmail("l@l.fr").setLastname("l").setFirstname("l").setRoles(Arrays.asList(Roles.GAMBLER));
            l.hashAndSetPassword("999");

            User k = new User().setEmail("k@k.fr").setLastname("k").setFirstname("k").setRoles(Arrays.asList(Roles.GAMBLER));
            k.hashAndSetPassword("999");

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
            e.setName("Euro 2016");
            e.setStart(new DateTime(2016, 6, 10, 21, 0).toDate());
            e.setEnd(new DateTime(2016, 7, 10, 23, 59).toDate());
            eventService.createOrUpdate(e);
            
            injectedCountries(countryService);

            injectedSport(sportService);

            /*Event e2 = new Event();
            e2.setName("Cdm 2015 Rugby");
            e2.setStart(DateTime.now().plusDays(5).toDate());
            e2.setEnd(DateTime.now().plusDays(15).toDate());
            eventService.createOrUpdate(e2);
//            MatchDAO matchDAO = new MatchDAOImpl();
            */

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
                match.setEvent(Key.create(Event.class, e.getId()));
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
	 * injection d'une liste de countries
	 * @param countryService
	 */
	private void injectedCountries(CountryService countryService) {
        for (CountryCode code : CountryCode.values())
        {
            countryService.createOrUpdate(new Country(code));
        }
        countryService.createOrUpdate(new Country(1000L, "Angleterre", "angleterre"));
		/* Country c = new Country();
		// p.setIdPays(idPays);
//		p.setIdPays((long)1);
		c.setName("france");
        c.setDisplayName("France");
		countryService.createOrUpdate(c); */
	}

    @Provides
    @Named("userService")
    public UserService getUserService(@Named("userServiceGAE") UserService userService) {
        return userService;
    }
}
