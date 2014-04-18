package fr.boillodmanuel.restx.gae.model;

import org.joda.time.DateTime;

public class Match {
	
	private DateTime date;
	
	private Pays participant1;
	
	private Pays participant2;

	public Match(DateTime date, Pays participant1, Pays participant2) {
		super();
		this.date = date;
		this.participant1 = participant1;
		this.participant2 = participant2;
	}

	public DateTime getDate() {
		return date;
	}

	public Match setDate(DateTime date) {
		this.date = date;
		return this;
	}

	public Pays getParticipant1() {
		return participant1;
	}

	public Match setParticipant1(Pays participant1) {
		this.participant1 = participant1;
		return this;
	}

	public Pays getParticipant2() {
		return participant2;
	}

	public Match setParticipant2(Pays participant2) {
		this.participant2 = participant2;
		return this;
	}
	
	@Override
	public boolean equals(Object match){
		if(!(match instanceof Match))return false;
		
		return ((Match)match).date.equals(this.getDate());
	}
	
	
	
	

}
