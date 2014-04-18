package fr.boillodmanuel.restx.gae.module;

import javax.inject.Named;

import fr.boillodmanuel.restx.gae.services.MockUserService;
import fr.boillodmanuel.restx.gae.services.MockZenFootUserRepository;
import fr.boillodmanuel.restx.gae.services.ZenFootUserRepository;
import fr.boillodmanuel.restx.user.User;
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
