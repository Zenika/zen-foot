package fr.boillodmanuel.restx.gae.dao.token;

import javax.inject.Named;

import restx.factory.Module;
import restx.factory.Provides;

@Module
public class TokenModule {
	
	@Provides
	@Named("tokenService")
	public TokenService buildTokenService(){
		return new TokenService();
	}

}
