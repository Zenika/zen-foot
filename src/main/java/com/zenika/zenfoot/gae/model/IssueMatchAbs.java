package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Subclass;

import javax.persistence.Embedded;

/**
 * This is a representation for the result of a match. The result of a match corresponds to
 * two Score objects, corresponding to the scores of each team participating to a match. This
 * representation can be that of an outcome, i.e. the actual result of a match, or that of
 * a prediction, i.e. a Bet on a match.
 *
 * Created by raphael on 28/04/14.
 */
@Subclass
public abstract class IssueMatchAbs {

    @Embedded
    protected Score score1;

    @Embedded
    protected Score score2;

    protected IssueMatchAbs() {
    }

    public Score getScore1() {
        return score1;
    }

    public void setScore1(Score score1) {
        this.score1 = score1;
    }

    public Score getScore2() {
        return score2;
    }

    public void setScore2(Score score2) {
        this.score2 = score2;
    }
}
