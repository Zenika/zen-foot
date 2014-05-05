package com.zenika.zenfoot.gae.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zenika.zenfoot.gae.jackson.ScoreDeserializer;
import com.zenika.zenfoot.gae.jackson.ScoreSerializer;

/**
 * Created by raphael on 28/04/14.
 */

public class Score {

    private final static int unrated=-1;


    protected Participant participant;

    @JsonSerialize(using = ScoreSerializer.class)
    @JsonDeserialize(using= ScoreDeserializer.class)
    protected int score=unrated;

    public Score() {
    }

    public Participant getParticipant() {
        return participant;
    }

    public Score setParticipant(Participant participant) {
        this.participant = participant;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Score setScore(int score) {
        this.score = score;
        return this;
    }

    public boolean isUnknown(){
        return score==unrated;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Score))return false;
        Score score = (Score) obj;
        return score.getParticipant().equals(this.getParticipant()) && (score.getScore() == this.getScore());
    }


}
