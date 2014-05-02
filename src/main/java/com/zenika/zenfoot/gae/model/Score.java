package com.zenika.zenfoot.gae.model;

/**
 * Created by raphael on 28/04/14.
 */

public class Score {


    protected Participant participant;

    protected int score=-1;

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
        return score==-1;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Score))return false;
        Score score = (Score) obj;
        return score.getParticipant().equals(this.getParticipant()) && (score.getScore() == this.getScore());
    }


}
