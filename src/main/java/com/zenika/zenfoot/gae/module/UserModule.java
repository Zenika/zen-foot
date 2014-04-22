package com.zenika.zenfoot.gae.module;

import javax.inject.Named;

import com.zenika.zenfoot.gae.services.MockUserService;
import com.zenika.zenfoot.gae.services.MockZenFootUserRepository;
import com.zenika.zenfoot.gae.services.ZenFootUserRepository;
import com.zenika.zenfoot.user.User;
import restx.factory.Module;
import restx.factory.Provides;
import restx.security.UserService;

@Module
public class UserModule {



	@Provides
	@Named("userRepository")
	public ZenFootUserRepository getUserRepository() {
		return new MockZenFootUserRepository();
	}


	@Provides
	@Named("userService")
	public UserService getUserService(@Named("userRepository") ZenFootUserRepository userRepository) {
		return new MockUserService(userRepository);
	}



}
