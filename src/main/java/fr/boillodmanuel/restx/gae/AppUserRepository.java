package fr.boillodmanuel.restx.gae;

import javax.inject.Named;

import fr.boillodmanuel.restx.gae.services.MockZenFootUserRepository;
import fr.boillodmanuel.restx.gae.services.ZenFootUserRepository;
import restx.factory.Component;
import restx.security.CredentialsStrategy;

import fr.boillodmanuel.restx.user.User;

//@Component
public class AppUserRepository extends MockZenFootUserRepository {
   /* public static final User defaultAdminUser = new User()
            .setEmail("admin@iwasthere.io")



    public AppUserRepository(@Named("userService") ZenFootUserRepository userService,
                             CredentialsStrategy credentialsStrategy) {
        super(userService,defaultAdminUser);
    }
    */
    
}
