package fr.boillodmanuel.restx.gae;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;
import restx.config.ConfigLoader;
import restx.config.ConfigSupplier;
import restx.factory.Module;
import restx.factory.Provides;
import restx.security.*;

import javax.inject.Named;

@Module
public class AppModule {
    @Provides
    public SignatureKey signatureKey() {
         return new SignatureKey("-7155845384686390950 gae_restx e21eb686-6e0d-4a30-8ad0-ca74107f423a gae_restx".getBytes(Charsets.UTF_8));
    }

    @Provides
    @Named("restx.admin.password")
    public String restxAdminPassword() {
        return "restx";
    }

    @Provides
    public ConfigSupplier appConfigSupplier(ConfigLoader configLoader) {
        // Load settings.properties in fr.boillodmanuel.restx.gae package as a set of config entries
        return configLoader.fromResource("fr.boillodmanuel.restx.gae/settings");
    }

    @Provides
    public CredentialsStrategy credentialsStrategy() {
        return new BCryptCredentialsStrategy();
    }

    @Provides
    public BasicPrincipalAuthenticator basicPrincipalAuthenticator(
            SecuritySettings securitySettings, CredentialsStrategy credentialsStrategy,
            @Named("restx.admin.passwordHash") String defaultAdminPasswordHash) {
        return new StdBasicPrincipalAuthenticator(new StdUserService<>(
                // use file based users repository.
                // Developer's note: prefer another storage mechanism for your users if you need real user management
                // and better perf
                new SimpleUserRepository<>(
                        StdUser.class, // this is the class for the User objects, that you can get in your app code
                        // with RestxSession.current().getPrincipal().get()
                        // it can be a custom user class, it just need to be json deserializable

                        // this is the default restx admin, useful to access the restx admin console.
                        // if one user with restx-admin role is defined in the repository, this default user won't be
                        // available anymore
                        new StdUser("admin", ImmutableSet.<String>of("*")),

                        "admin",
                        // admin username

                        "$2a$10$9AkQpUHb5.hI5CPQO9.xrOiqOWZ75Jcn9tZTdSu8mEZ5jLxRYyxWq"
                        // hash of 'admin' password
                        ),
                credentialsStrategy, defaultAdminPasswordHash),
                securitySettings);
    }
}
