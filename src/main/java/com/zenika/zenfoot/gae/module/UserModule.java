package com.zenika.zenfoot.gae.module;

import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import restx.admin.AdminModule;
import restx.factory.Module;
import restx.factory.Provides;
import restx.security.UserService;

import com.google.appengine.api.utils.SystemProperty;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.dao.PaysDAO;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.model.Pays;
import com.zenika.zenfoot.gae.services.GamblerRepository;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.MatchService;
import com.zenika.zenfoot.gae.services.MockUserService;
import com.zenika.zenfoot.gae.services.MockZenFootUserRepository;
import com.zenika.zenfoot.gae.services.PaysService2;
import com.zenika.zenfoot.user.User;

@Module
public class UserModule {


    @Provides
    @Named("userRepository")
    public MockZenFootUserRepository getUserRepository(GamblerRepository gamblerRepository) {
        MockZenFootUserRepository mockZenFootUserRepository = new MockZenFootUserRepository();

        return new MockZenFootUserRepository();
    }




    @Provides
    @Named("userServiceGAE")
    public UserService getUserService2(@Named("userRepository") MockZenFootUserRepository userRepository, GamblerService gamblerService, MatchService matchService, @Named("paysService") PaysService2 paysService) {
        MockUserService userService = new MockUserService(userRepository);

        if(SystemProperty.environment.value()== SystemProperty.Environment.Value.Development) {
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


            userService.createUser(admin);
            userService.createUser(jean);
            userService.createUser(mira);
            userService.createUser(bill);
            userService.createUser(andy);
            userService.createUser(sophie);
            userService.createUser(kate);
            userService.createUser(olivier);
            userService.createUser(russell);
            userService.createUser(harold);
            userService.createUser(richard);
            userService.createUser(jc);
            userService.createUser(leonardo);
            userService.createUser(j);
            userService.createUser(k);
            userService.createUser(l);


            List<Match> matchs = matchService.getAll();
            gamblerService.createGambler(jean, matchs,13);
            gamblerService.createGambler(mira, matchs, 12);
            gamblerService.createGambler(bill, matchs, 12);
            gamblerService.createGambler(andy, matchs, 12);
            gamblerService.createGambler(sophie, matchs, 12);
            gamblerService.createGambler(kate, matchs, 12);
            gamblerService.createGambler(olivier, matchs, 95);
            gamblerService.createGambler(russell, matchs, 95);
            gamblerService.createGambler(harold, matchs, 29);
            gamblerService.createGambler(richard, matchs, 65);
            gamblerService.createGambler(jc, matchs, 28);
            gamblerService.createGambler(leonardo, matchs, 18);
            gamblerService.createGambler(j, matchs,13);
            gamblerService.createGambler(k, matchs,1);
            gamblerService.createGambler(l, matchs);
            
            
            
    		Pays p = new Pays();
    		// p.setIdPays(idPays);
    		p.setIdPays((long)1);
    		p.setNomPays("FRANCE");
    		paysService.createOrUpdate(p);
    		
        }
        return userService;

    }

    @Provides
    @Named("userService")
    public UserService getUserService(@Named("userServiceGAE") UserService userService) {
        return userService;
    }

    
    //DAOs
    @Provides
    @Named("paysDAO")
    public PaysDAO paysDAO() {
        return new PaysDAO();
    }	 
	
    @Provides
    @Named("paysService")
    public PaysService2 paysService(@Named("paysDAO") PaysDAO paysDAO){
        return new PaysService2(paysDAO);
    }
}
