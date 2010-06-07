package com.zenika.zenfoot.model;

import java.io.Serializable;
import java.util.Date;

public class Game extends AbstractModel implements Serializable,
		Comparable<Game> {
	private Team team1;
	private Team team2;
	private Date kickoff;
	private int goalsForTeam1 = -1;
	private int goalsForTeam2 = -1;

	public Game() {
	}

	public Game(Team team1, Team team2, Date kickoff) {
		assert team1 != null : "new Match: team1 must not be null";
		assert team2 != null : "new Match: team2 must not be null";
		assert kickoff != null : "new Match: kickoff must not be null";

		this.team1 = team1;
		this.team2 = team2;
		this.kickoff = kickoff;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Game other = (Game) obj;
		if (this.team1 != other.team1
				&& (this.team1 == null || !this.team1.equals(other.team1))) {
			return false;
		}
		if (this.team2 != other.team2
				&& (this.team2 == null || !this.team2.equals(other.team2))) {
			return false;
		}
		if (this.kickoff != other.kickoff
				&& (this.kickoff == null || !this.kickoff.equals(other.kickoff))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 37 * hash + (this.team1 != null ? this.team1.hashCode() : 0);
		hash = 37 * hash + (this.team2 != null ? this.team2.hashCode() : 0);
		hash = 37 * hash + (this.kickoff != null ? this.kickoff.hashCode() : 0);
		return hash;
	}

	public int getGoalsForTeam1() {
		return goalsForTeam1;
	}

	public void setGoalsForTeam1(int goalsForTeam1) {
		this.goalsForTeam1 = goalsForTeam1;
	}

	public int getGoalsForTeam2() {
		return goalsForTeam2;
	}

	public void setGoalsForTeam2(int goalsForTeam2) {
		this.goalsForTeam2 = goalsForTeam2;
	}

	public Team getTeam1() {
		return team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public Date getKickoff() {
		return kickoff;
	}

	public void setKickoff(Date kickoff) {
		this.kickoff = kickoff;
	}

	@Override
	public String toString() {
		return "Match: " + team1 + "-" + team2 + " at " + kickoff;
	}

	public int compareTo(Game otherMatch) {
		return kickoff.compareTo(otherMatch.kickoff);
	}

	public boolean hasGoalsSet() {
		return goalsForTeam1 >= 0 && goalsForTeam2 >= 0;
	}
}
