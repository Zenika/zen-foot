package fr.boillodmanuel.restx.gae.module;

import javax.inject.Named;

import fr.boillodmanuel.restx.gae.services.BetService;
import fr.boillodmanuel.restx.gae.services.MatchService;
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
