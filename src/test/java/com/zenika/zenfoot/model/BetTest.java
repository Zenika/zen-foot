package com.zenika.zenfoot.model;

import junit.framework.TestCase;
import static com.zenika.zenfoot.model.MatchTest.sampleMatch;

public class BetTest extends TestCase {
    public void testBetMustHoldAUser() throws AssertionError {
        try {
            new Bet(null, sampleMatch());
            fail();
        } catch (AssertionError ae) {
            assertEquals("new Bet: user must not be null", ae.getMessage());
        }
    }

    public void testBetMustHoldAMatch() throws AssertionError {
        try {
            new Bet(new Player("email"), null);
            fail();
        } catch (AssertionError ae) {
            assertEquals("new Bet: match must not be null", ae.getMessage());
        }
    }
}
