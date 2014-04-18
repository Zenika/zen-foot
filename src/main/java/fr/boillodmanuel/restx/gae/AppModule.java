package fr.boillodmanuel.restx.gae;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;

import fr.boillodmanuel.restx.gae.services.MockUserService;
import fr.boillodmanuel.restx.gae.services.ZenFootUserRepository;
import restx.config.ConfigLoader;
import restx.config.ConfigSupplier;
import restx.factory.Module;
import restx.factory.Provides;
import restx.security.*;

import javax.inject.Named;

@Module
public class AppModule {
	
	public static final String ADMIN = "admin";
    public static final String USER = "user";

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
            @Named("userService") UserService userService, SecuritySettings securitySettings,
            CredentialsStrategy credentialsStrategy,
            @Named("restx.admin.passwordHash") String adminPasswordHash) {
        return new StdBasicPrincipalAuthenticator(
                userService, securitySettings);
    }
    
   
}
