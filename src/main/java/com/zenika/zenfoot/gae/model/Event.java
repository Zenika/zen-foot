package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.List;

/**
 * Created by raphael on 25/08/14.
 * <p/>
 * An event corresponds to any football event. It is used to archive the result of former events.
 */
@Entity
public class Event {

    @Id
    private Long id;

    private List<GamblerRanking> gamblerRankings;

    private List<Match> matches;

    private List<String> pays;

    private List<TeamRanking> teamRankings;

    @Index
    private String name;

    public Event() {
    }

    public List<GamblerRanking> getGamblerRankings() {
        return gamblerRankings;
    }

    public void setGamblerRankings(List<GamblerRanking> gamblerRankings) {
        this.gamblerRankings = gamblerRankings;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public void setPays(List<String> pays) {
        this.pays = pays;
    }

    public List<String> getPays() {
        return pays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TeamRanking> getTeamRankings() {
        return teamRankings;
    }

    public void setTeamRankings(List<TeamRanking> teamRankings) {
        this.teamRankings = teamRankings;
    }
}
