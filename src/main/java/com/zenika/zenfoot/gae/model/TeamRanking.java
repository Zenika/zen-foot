package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by raphael on 25/08/14.
 */
@Entity
public class TeamRanking {

    @Id
    private Long id;

    @Index
    private Long teamId;

    @Index
    private double points;

    public TeamRanking() {
    }

    public Long getId() {
        return id;
    }

    public TeamRanking setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getTeamId() {
        return teamId;
    }

    public TeamRanking setTeamId(Long teamId) {
        this.teamId = teamId;
        return this;
    }

    public double getPoints() {
        return points;
    }

    public TeamRanking setPoints(double points) {
        this.points = points;
        return this;
    }

    public double addPoints(double points){
        this.points += points;
        return this.points;
    }
}
