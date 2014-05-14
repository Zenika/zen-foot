package com.zenika.zenfoot.gae.model;

/**
 * The actual outcome of a match. A match can have only one outcome. This class extends
 * IssueMatchAbs which is the abstract representation of the result of a match.
 * Created by raphael on 28/04/14.
 */
public class MatchOutcome extends IssueMatchAbs implements Outcome{


    public MatchOutcome() {
        this(Score.getUnknown(),Score.getUnknown());
    }

    public MatchOutcome(Score score1,Score score2){
        this.setScore1(score1);
        this.setScore2(score2);
    }

    @Override
    public boolean isLike(Prediction prediction) {
        //TODO implement method
        return false;
    }

    @Override
    public boolean hasHappened() {
        return false;
    }
}
