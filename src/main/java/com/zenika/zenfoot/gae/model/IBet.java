package com.zenika.zenfoot.gae.model;

/**
 * Created by raphael on 24/04/14.
 */
public interface IBet extends Prediction {

    boolean isWon();

    /**
     * Is used to know whether the gambler made a bet on this.
     * @return true if the gambler bet something, false otherwise
     */
    boolean wasMade();

    boolean isLike1Point(Outcome outcome);

    boolean isLike3Points(Outcome outcome);
}
