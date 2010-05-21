package com.zenika.zenfoot.model;

import junit.framework.TestCase;
import static com.zenika.zenfoot.model.MatchTest.sampleMatch;

public class BetTest extends TestCase {
    public void testBetForGoal1MustBePositive() throws AssertionError {
        try {
            new Bet(new User("test"), sampleMatch(), -1, 0);
            fail();
        } catch (AssertionError ae) {
            assertEquals("new Bet: goals1 must be positive", ae.getMessage());
        }
    }

    public void testBetForGoal2MustBePositive() throws AssertionError {
        try {
            new Bet(new User("test"), sampleMatch(), 0, -1);
            fail();
        } catch (AssertionError ae) {
            assertEquals("new Bet: goals2 must be positive", ae.getMessage());
        }
    }

    public void testBetMustHoldAUser() throws AssertionError {
        try {
            new Bet(null, sampleMatch(), 0, 0);
            fail();
        } catch (AssertionError ae) {
            assertEquals("new Bet: user must not be null", ae.getMessage());
        }
    }

    public void testBetMustHoldAMatch() throws AssertionError {
        try {
            new Bet(new User("email"), null, 0, 0);
            fail();
        } catch (AssertionError ae) {
            assertEquals("new Bet: match must not be null", ae.getMessage());
        }
    }
}
