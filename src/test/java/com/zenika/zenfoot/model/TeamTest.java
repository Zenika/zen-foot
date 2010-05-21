package com.zenika.zenfoot.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import junit.framework.TestCase;

public class TeamTest extends TestCase {
    public static final int FIRST = 0;

    public void testTeamNameMustNotBeNull() throws AssertionError {
        try {
            new Team(null, "fr.png");
            fail();
        } catch (AssertionError ae) {
            assertEquals("new Team: name must not be null", ae.getMessage());
        }
    }

    public void testTeamImageNameMustNotBeNull() throws AssertionError {
        try {
            new Team("France", null);
            fail();
        } catch (AssertionError ae) {
            assertEquals("new Team: imageName must not be null", ae.getMessage());
        }
    }

    public void testTeamsMustBeCorectlySortedByName() {
        Team france = new Team("France", "fr.png");
        Team usa = new Team("usa", "us.png");
        List<Team> teams = new ArrayList<Team>();
        teams.add(usa);
        teams.add(france);

        assertTrue(usa.equals(teams.get(FIRST)));

        Collections.sort(teams);

        assertTrue(france.equals(teams.get(FIRST)));
    }

    public static Team sampleTeam() {
        return new Team("France", "fr.png");
    }
}
