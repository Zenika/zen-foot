package com.zenika.zenfoot.gae.model;

/**
 * Created by raphael on 28/04/14.
 */
public interface Outcome {

    boolean isLike(Prediction prediction);

    boolean hasHappened();
}
