package com.zenika.zenfoot.gae.model;

/**
 * Created by raphael on 28/04/14.
 */
public interface Outcome {

    boolean isLike(Prediction prediction);

    boolean hasHappened();

    /**
     * We want to know if an outcome was updated by an administrator to prevent him from updating the value
     * several times. Indeed we want to prevent such things from happening, as the points for gamblers are calculated
     * once the outcome of the match is given by the admin.
     * @return
     */
    boolean updated();
}
