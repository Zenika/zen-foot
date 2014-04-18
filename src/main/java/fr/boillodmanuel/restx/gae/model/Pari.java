package fr.boillodmanuel.restx.gae.model;

public class Pari {

	private Pays gagnant;

	private Resultat resultat;

	private Match match;

	public Pari(Pays gagnant, Resultat resultat, Match match) {
		super();
		this.gagnant = gagnant;
		this.resultat = resultat;
		this.match = match;
	}

	public Pays getGagnant() {
		return gagnant;
	}

	public void setGagnant(Pays gagnant) {
		this.gagnant = gagnant;
	}

	public Resultat getResultat() {
		return resultat;
	}

	public void setResultat(Resultat resultat) {
		this.resultat = resultat;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

}
