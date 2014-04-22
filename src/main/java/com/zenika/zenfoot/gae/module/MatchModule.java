package com.zenika.zenfoot.gae.module;

import javax.inject.Named;

import com.zenika.zenfoot.gae.services.BetService;
import com.zenika.zenfoot.gae.services.MatchService;
import restx.factory.Module;
import restx.factory.Provides;

@Module
public class MatchModule {

	private MatchService matchService = new MatchService();

	@Provides
	@Named("listematchs")
	public MatchService getMatchs() {
		return matchService;
	}
	
	@Provides
	@Named("bets")
	public BetService getBets(@Named("listematchs") MatchService service){
		return new BetService(service);
	}

}
