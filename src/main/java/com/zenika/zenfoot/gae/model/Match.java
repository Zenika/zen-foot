package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import org.joda.time.DateTime;

@Entity
public class Match implements Event {

    @Id
    private Long id;

    private DateTime date;

    private Participant participant1;

    protected Participant participant2;

    protected MatchOutcome outcome;

    public Match(DateTime date, Participant participant1, Participant participant2) {
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

    public Participant getParticipant1() {
        return participant1;
    }

    public Match setParticipant1(Participant participant1) {
        this.participant1 = participant1;
        return this;
    }

    public Participant getParticipant2() {
        return participant2;
    }

    public Match setParticipant2(Participant participant2) {
        this.participant2 = participant2;
        return this;
    }


    public MatchOutcome getOutcome() {
        return outcome;
    }

    public Match setOutcome(MatchOutcome outcome) {
        this.outcome = outcome;
        return this;
    }

    public Match setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object match) {
        if (!(match instanceof Match)) return false;

        return ((Match) match).date.equals(this.getDate());
    }


    @Override
    public boolean endedLike(Prediction prediction) {
        //TODO implement method
        return false;
    }
}
