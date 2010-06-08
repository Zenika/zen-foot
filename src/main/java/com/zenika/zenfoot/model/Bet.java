package com.zenika.zenfoot.model;

import java.io.Serializable;

public class Bet extends AbstractModel implements Serializable {

    private Player player;
    private Match match;
    private int goalsForTeam1 = -1;
    private int goalsForTeam2 = -1;

    public Bet() {
    }

    public Bet(Player player, Match match) {
        assert player != null : "new Bet: user must not be null";
        assert match != null : "new Bet: match must not be null";

        this.player = player;
        this.match = match;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + player + "-" + match;
    }

    public boolean isBetSet() {
        return goalsForTeam1 >= 0 && goalsForTeam2 >= 0;
    }

    public void setGoals(int goalsForTeam1, int goalsForTeam2) {
        this.goalsForTeam1 = goalsForTeam1;
        this.goalsForTeam2 = goalsForTeam2;
    }
}
