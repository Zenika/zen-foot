package com.zenika.zenfoot.gae;

import javax.inject.Named;

import com.zenika.zenfoot.gae.services.MockZenFootUserRepository;
import com.zenika.zenfoot.gae.services.ZenFootUserRepository;
import restx.factory.Component;
import restx.security.CredentialsStrategy;

import com.zenika.zenfoot.user.User;

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
