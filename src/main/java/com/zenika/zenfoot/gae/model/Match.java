package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;
import java.io.Serializable;
import org.joda.time.DateTime;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Match implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
    private Long id;
    private DateTime date;
    private String groupe;

    private Country team1;
    private Country team2;

    private Integer score1;
    private Integer score2;
    private boolean scoreUpdated = false;
    
    @Parent private Key<Event> event;

    private Sport sport;

	public Match() {
    }

    public Match(DateTime date, String groupe, Country team1, Country team2) {
        this.date = date;
        this.groupe = groupe;
        this.team1 = team1;
        this.team2 = team2;
    }


    public Match(DateTime date, String groupe, Country team1, Country team2,
			Integer score1, Integer score2, boolean scoreUpdated, Sport sport) {
		super();
		this.date = date;
		this.groupe = groupe;
		this.team1 = team1;
		this.team2 = team2;
		this.score1 = score1;
		this.score2 = score2;
		this.scoreUpdated = scoreUpdated;
		this.sport = sport;
	}

	public Match(DateTime dateTime, String groupe, Country team1,
                 Country team2, Sport sport) {
		this.date = dateTime;
		this.groupe = groupe;
		this.team1 = team1;
		this.team2 = team2;
		this.sport = sport;
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

    public Country getTeam1() {
        return team1;
    }

    public void setTeam1(Country team1) {
        this.team1 = team1;
    }

    public Country getTeam2() {
        return team2;
    }

    public void setTeam2(Country team2) {
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

    public Key<Event> getEvent() {
        return event;
    }

    public void setEvent(Key<Event> event) {
        this.event = event;
    }
    /**
     * @return the sport
     */
    public Sport getSport() {
            return sport;
    }

    /**
     * @param sport the sport to set
     */
    public void setSport(Sport sport) {
            this.sport = sport;
    }
}
