package fr.boillodmanuel.restx.gae.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Named;

import restx.factory.Component;
import fr.boillodmanuel.restx.gae.model.Pari;
import fr.boillodmanuel.restx.gae.model.Pays;
import fr.boillodmanuel.restx.gae.model.Resultat;
import fr.boillodmanuel.restx.user.User;

@Component
public class BetService {

	private HashMap<User, List<Pari>> paris = new HashMap<>();

	public BetService(@Named("listematchs") MatchService matchService) {
		// Filling service with mock data
		User raphael = new User().setEmail("raphael.martignoni@zenika.com")
				.setName("raphael");
		Pari pari1 = new Pari(new Pays().setName("Bresil"), new Resultat(5, 1),
				matchService.getMatch(0));
		Pari pari2 = new Pari(new Pays().setName("Mexique"),
				new Resultat(2, 0), matchService.getMatch(1));
		ArrayList<Pari> paris = new ArrayList<>();
		paris.add(pari1);
		paris.add(pari2);

		this.paris.put(raphael, paris);
	}

	public List<Pari> getBets(User user) {
		return this.paris.get(user);
	}

}
