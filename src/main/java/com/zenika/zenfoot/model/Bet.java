package com.zenika.zenfoot.model;

import java.io.Serializable;

public class Bet extends AbstractModel implements Serializable {
    private User user;
    private Game game;
    private int goalsForTeam1 = -1;
    private int goalsForTeam2 = -1;
    
    public Bet() {
	}

    public Bet(User user, Game match, int goalsForTeam1, int goalsForTeam2) {
        assert goalsForTeam1 >= 0 : "new Bet: goals1 must be positive";
        assert goalsForTeam2 >= 0 : "new Bet: goals2 must be positive";
        assert user != null : "new Bet: user must not be null";
        assert match != null : "new Bet: match must not be null";

        this.user = user;
        this.setGame(match);
        this.goalsForTeam1 = goalsForTeam1;
        this.goalsForTeam2 = goalsForTeam2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bet other = (Bet) obj;
        if (this.user != other.user && (this.user == null || !this.user.equals(other.user))) {
            return false;
        }
        if (this.getGame() != other.getGame() && (this.getGame() == null || !this.getGame().equals(other.getGame()))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.user != null ? this.user.hashCode() : 0);
        hash = 89 * hash + (this.getGame() != null ? this.getGame().hashCode() : 0);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Bet: " + user + "-" + getGame();
    }

    public boolean isBetSet() {
        return goalsForTeam1 >= 0 && goalsForTeam2 >= 0;
    }

    public void setGoals(int goalsForTeam1, int goalsForTeam2) {
        this.goalsForTeam1 = goalsForTeam1;
        this.goalsForTeam2 = goalsForTeam2;
    }

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}
}
