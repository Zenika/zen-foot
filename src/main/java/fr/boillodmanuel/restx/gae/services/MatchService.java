package fr.boillodmanuel.restx.gae.services;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import fr.boillodmanuel.restx.gae.model.Match;
import fr.boillodmanuel.restx.gae.model.Pays;

public class MatchService {

	private List<Match> matchs;

	public MatchService() {
		this.matchs = new ArrayList<>();
		Pays bresil = new Pays().setName("Brésil");
		Pays croatie = new Pays().setName("Croatie");
		Match match1 = new Match(new DateTime(2014, 06, 12, 17, 0), bresil,
				croatie);

		Pays mexique = new Pays().setName("Mexique");
		Pays cameroun = new Pays().setName("Cameroun");
		Match match2 = new Match(new DateTime(2014, 06, 13, 18, 0), mexique,
				cameroun);

		matchs.add(match1);
		matchs.add(match2);
	}

	public List<Match> getMatchs() {
		return this.matchs;
	}

	public Match getMatch(int index) {
		return this.matchs.get(index);
	}

}
