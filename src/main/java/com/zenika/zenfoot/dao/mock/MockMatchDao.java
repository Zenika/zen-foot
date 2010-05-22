package com.zenika.zenfoot.dao.mock;

import com.zenika.zenfoot.dao.MatchDao;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.Team;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MockMatchDao implements MatchDao {
    private static final ThreadLocal<MockMatchDao> dao = new ThreadLocal<MockMatchDao>() {
        @Override
        protected MockMatchDao initialValue() {
            return new MockMatchDao();
        }
    };
    private List<Match> matchs = new ArrayList<Match>();
    private Date now = new GregorianCalendar(2010, 5, 2, 12, 01).getTime();

    public static MockMatchDao get() {
        return dao.get();
    }

    public MockMatchDao() {
        matchs.add(match("Italie", "Russie", "4/6/2010 19:02"));
        matchs.add(match("Argentine", "Pays-Bas", "4/6/2010 13:03"));
        matchs.add(match("Perou", "Chine", "3/6/2010 12:04"));
        matchs.add(match("Espagne", "Italie", "2/6/2010 12:00"));
        matchs.add(match("USA", "Algérie", "2/6/2010 10:00"));
        matchs.add(match("France", "Belgique", "1/6/2010 14:02", 5, 0));
        matchs.add(match("France", "Angleterre", "1/6/2010 12:03", 1, 3));
    }

    public static Match match(String team1, String team2, String kickoff, int goals1, int goals2) {
        Match match = match(team1, team2, kickoff);
        match.setGoalsForTeam1(goals1);
        match.setGoalsForTeam2(goals2);
        return match;
    }

    public static Match match(String team1, String team2, String kickoff) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy H:m", Locale.FRENCH);
            Match match = new Match(new Team(team1, "fr.png"), new Team(team2, "it.png"), dateFormat.parse(kickoff));
            return match;
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Match> find() {
        return matchs;
    }

    public List<Match> findPast() {
        List<Match> past = new ArrayList<Match>();
        for (Match match : matchs) {
            if (now.after(match.getKickoff()) && match.hasGoalsSet()) {
                past.add(match);
            }
        }
        return past;
    }

    public List<Match> findRunning() {
        List<Match> running = new ArrayList<Match>();
        for (Match match : matchs) {
            if (now.after(match.getKickoff()) && !match.hasGoalsSet()) {
                running.add(match);
            }
        }
        return running;
    }

    public List<Match> findIncoming() {
        List<Match> running = new ArrayList<Match>();
        for (Match match : matchs) {
            if (now.before(match.getKickoff())) {
                running.add(match);
            }
        }
        return running;
    }

    public Match save(Match model) {
        matchs.add(model);
        return model;
    }
}
