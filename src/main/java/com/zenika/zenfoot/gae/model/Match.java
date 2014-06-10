package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import org.joda.time.DateTime;

@Entity
public class Match {

    @Id
    private Long id;
    private DateTime date;
    private String groupe;

    private String team1;
    private String team2;

    private Integer score1;
    private Integer score2;
    private boolean scoreUpdated = false;

    public Match() {
    }

    public Match(DateTime date, String groupe, String team1, String team2) {
        this.date = date;
        this.groupe = groupe;
        this.team1 = team1;
        this.team2 = team2;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public Integer getScore1() {
        return score1;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public boolean isScoreUpdated() {
        return scoreUpdated;
    }

    public void setScoreUpdated(boolean scoreUpdated) {
        this.scoreUpdated = scoreUpdated;
    }

    @Override
    public String toString() {
        return team1 + " / " + team2 + " (" + date + ")";
    }
}
