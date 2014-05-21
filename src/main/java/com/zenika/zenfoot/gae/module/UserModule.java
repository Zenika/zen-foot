package com.zenika.zenfoot.gae.module;

import javax.inject.Named;

import com.google.common.base.Optional;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.services.*;
import com.zenika.zenfoot.user.User;
import restx.admin.AdminModule;
import restx.factory.Module;
import restx.factory.Provides;
import restx.security.UserService;

import java.util.Arrays;

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
        User raphael = new User().setName("Martignoni").setPrenom("Raphaël").setEmail(
                "raphael.martignoni@zenika.com").setRoles(Arrays.asList(Roles.ADMIN, AdminModule.RESTX_ADMIN_ROLE));

        //raphael.setLastUpdated(DateTime.now());
        raphael.setPasswordHash("2205");

        User jean = new User().setName("Bon").setPrenom("Jean").setEmail("jean.bon@zenika.com").setRoles(Arrays.asList(Roles.GAMBLER));
        jean.setPasswordHash("999");

        MockUserService userService = new MockUserService(userRepository);
        userService.createUser(raphael);
        userService.createUser(jean);

        gamblerService.createGambler(jean,matchService.getMatchs());
		return userService;
	}

    @Provides
    @Named("userServiceGAE")
    public UserService getUserService2(@Named("userRepository") MockZenFootUserRepository userRepository, GamblerService gamblerService,MatchService matchService) {
        return new MockUserService(userRepository);
    }




    }
