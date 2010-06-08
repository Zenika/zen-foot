package com.zenika.zenfoot.dao.mock;

import com.zenika.zenfoot.dao.GameDao;
import com.zenika.zenfoot.model.Match;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.zenika.zenfoot.dao.mock.MockUtil.matchs;
import static com.zenika.zenfoot.dao.mock.MockUtil.persist;

public class MockGameDao implements GameDao {
    public List<Match> find() {
        return matchs();
    }

    public List<Match> findPast() {
        List<Match> past = new ArrayList<Match>();
        for (Match match : matchs()) {
            if (now().after(match.getKickoff()) && match.hasGoalsSet()) {
                past.add(match);
            }
        }
        return past;
    }

    public List<Match> findRunning() {
        List<Match> running = new ArrayList<Match>();
        for (Match match : matchs()) {
            if (now().after(match.getKickoff()) && !match.hasGoalsSet()) {
                running.add(match);
            }
        }
        return running;
    }

    public List<Match> findIncoming() {
        List<Match> running = new ArrayList<Match>();
        for (Match match : matchs()) {
            if (now().before(match.getKickoff())) {
                running.add(match);
            }
        }
        return running;
    }

    public Match save(Match model) {
        if (!matchs().contains(model)) {
            matchs().add(model);
        }
        persist();
        return model;
    }

    public void delete(Match model) {
        matchs().remove(model);
        persist();
    }

    private Date now() {
        return new Date();
    }

    @Override
    public Match get(Match match) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
