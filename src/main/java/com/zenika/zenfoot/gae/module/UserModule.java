package com.zenika.zenfoot.gae.module;

import javax.inject.Named;

import com.google.common.base.Optional;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.services.*;
import com.zenika.zenfoot.user.User;
import restx.admin.AdminModule;
import restx.factory.Module;
import restx.factory.Provides;
import restx.security.UserService;

import java.util.Arrays;
import java.util.List;

@Module
public class UserModule {



	@Provides
	@Named("userRepository")
	public MockZenFootUserRepository getUserRepository(GamblerRepository gamblerRepository) {
        MockZenFootUserRepository mockZenFootUserRepository=new MockZenFootUserRepository();

		return new MockZenFootUserRepository();
	}


	@Provides
	@Named("userServiceDev")
	public UserService getUserService(@Named("userRepository") MockZenFootUserRepository userRepository, GamblerService gamblerService,MatchService matchService) {
        User raphael = new User().setName("admin").setPrenom("admin").setEmail(
                "admin@zenika.com").setRoles(Arrays.asList(Roles.ADMIN, AdminModule.RESTX_ADMIN_ROLE));

        //raphael.setLastUpdated(DateTime.now());
        raphael.setPasswordHash("2205");

        User jean = new User().setName("Bon").setPrenom("Jean").setEmail("jean.bon@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
        jean.setPasswordHash("999");

        User mira = new User().setName("Sorvino").setPrenom("Mira").setEmail("mira.sorvino@hollywood.com").setRoles(Arrays.asList(Roles.GAMBLER));
        mira.setPasswordHash("999");

        User bill =  new User().setName("Murray").setPrenom("Bill").setEmail("bill.murray@punxsutawney.com").setRoles(Arrays.asList(Roles.GAMBLER));
        bill.setPasswordHash("999");

        User andy=new User().setPrenom("Andy").setName("Mc-Dowell").setEmail("andy.mc-dowel@punxsutawney.com").setRoles(Arrays.asList(Roles.GAMBLER));
        andy.setPasswordHash("999");

        User sophie = new User().setPrenom("Sophie").setName("Marceau").setEmail("sophie.marceau@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
        sophie.setPasswordHash("999");

        User kate = new User().setPrenom("Kate").setName("Winslet").setEmail("kate.winslet@revolutionary-road.com").setRoles(Arrays.asList(Roles.GAMBLER));
        kate.setPasswordHash("999");

        User olivier = new User().setPrenom("Olivier").setName("Martinez").setEmail("olivier.martinez@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
        olivier.setPasswordHash("999");

        User russell  = new User().setPrenom("Russell").setName("Crowe").setEmail("russell.crowe@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
        russell.setPasswordHash("999");

        User harold = new User().setPrenom("Harold").setName("Ramis").setEmail("harold.ramis@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
        harold.setPasswordHash("999");

        User richard = new User().setPrenom("Richard").setName("Virenque").setEmail("richard.virenque@quickstep.fr").setRoles(Arrays.asList(Roles.GAMBLER));
        richard.setPasswordHash("999");

        User jc = new User().setPrenom("Jean-Claude").setName("Duss").setEmail("jean-claude.duss@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
        jc.setPasswordHash("999");

        User leonardo = new User().setPrenom("Leonardo").setName("Di-Caprio").setEmail("leonardo.di-caprio@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
        leonardo.setPasswordHash("999");

        MockUserService userService = new MockUserService(userRepository);
        userService.createUser(raphael);
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

        List<Match> matchs = matchService.getMatchs();
        gamblerService.createGambler(jean,matchs);
        gamblerService.createGambler(mira,matchs,12);
        gamblerService.createGambler(bill,matchs,15);
        gamblerService.createGambler(andy,matchs,58);
        gamblerService.createGambler(sophie,matchs,47);
        gamblerService.createGambler(kate,matchs,18);
        gamblerService.createGambler(olivier,matchs,95);
        gamblerService.createGambler(russell,matchs,35);
        gamblerService.createGambler(harold,matchs,29);
        gamblerService.createGambler(richard,matchs,65);
        gamblerService.createGambler(jc,matchs,28);
        gamblerService.createGambler(leonardo,matchs,18);
		return userService;
	}

    @Provides
    @Named("userServiceGAE")
    public UserService getUserService2(@Named("userRepository") MockZenFootUserRepository userRepository, GamblerService gamblerService,MatchService matchService) {
        return new MockUserService(userRepository);
    }




    }
