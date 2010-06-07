package com.zenika.zenfoot.model;

import java.io.Serializable;

public class Bet extends AbstractModel implements Serializable {
    private User userId;
    private Game gameId;
    private int goalsForTeam1 = -1;
    private int goalsForTeam2 = -1;
    
    public Bet() {
	}

    public Bet(User user, Game match, int goalsForTeam1, int goalsForTeam2) {
        assert goalsForTeam1 >= 0 : "new Bet: goals1 must be positive";
        assert goalsForTeam2 >= 0 : "new Bet: goals2 must be positive";
        assert user != null : "new Bet: user must not be null";
        assert match != null : "new Bet: match must not be null";

        this.userId = user;
        this.setGameId(match);
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
        if (this.userId != other.userId && (this.userId == null || !this.userId.equals(other.userId))) {
            return false;
        }
        if (this.getGameId() != other.getGameId() && (this.getGameId() == null || !this.getGameId().equals(other.getGameId()))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.userId != null ? this.userId.hashCode() : 0);
        hash = 89 * hash + (this.getGameId() != null ? this.getGameId().hashCode() : 0);
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User user) {
        this.userId = user;
    }

    @Override
    public String toString() {
        return "Bet: " + userId + "-" + getGameId();
    }

    public boolean isBetSet() {
        return goalsForTeam1 >= 0 && goalsForTeam2 >= 0;
    }

    public void setGoals(int goalsForTeam1, int goalsForTeam2) {
        this.goalsForTeam1 = goalsForTeam1;
        this.goalsForTeam2 = goalsForTeam2;
    }

	public void setGameId(Game game) {
		this.gameId = game;
	}

	public Game getGameId() {
		return gameId;
	}
}
