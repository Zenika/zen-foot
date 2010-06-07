package com.zenika.zenfoot.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import junit.framework.TestCase;
import static com.zenika.zenfoot.model.TeamTest.sampleTeam;

public class MatchTest extends TestCase {
    public static final int FIRST = 0;

    public void testMatchTeam1MustNotBeNull() throws AssertionError {
        try {
            new Game(null, sampleTeam(), new Date());
            fail();
        } catch (AssertionError ae) {
            assertEquals("new Match: team1 must not be null", ae.getMessage());
        }
    }

    public void testMatchTeam2MustNotBeNull() throws AssertionError {
        try {
            new Game(sampleTeam(), null, new Date());
            fail();
        } catch (AssertionError ae) {
            assertEquals("new Match: team2 must not be null", ae.getMessage());
        }
    }

    public void testMatchKickOffMustNotBeNull() throws AssertionError {
        try {
            new Game(sampleTeam(), sampleTeam(), null);
            fail();
        } catch (AssertionError ae) {
            assertEquals("new Match: kickoff must not be null", ae.getMessage());
        }
    }

    public void testMatchsMustBeCorectlySortedByKickOffDate() {
        Game matchOld = new Game(sampleTeam(), sampleTeam(), new GregorianCalendar(2000, 05, 23).getTime());
        Game matchRecent = new Game(sampleTeam(), sampleTeam(), new GregorianCalendar(2010, 05, 23).getTime());
        List<Game> matchs = new ArrayList<Game>();
        matchs.add(matchRecent);
        matchs.add(matchOld);

        assertTrue(matchRecent.equals(matchs.get(FIRST)));

        Collections.sort(matchs);

        assertTrue(matchOld.equals(matchs.get(FIRST)));
    }

    public static Game sampleMatch() {
        return new Game(sampleTeam(), sampleTeam(), new Date());
    }
}
