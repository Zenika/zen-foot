package com.zenika.zenfoot.dao.mock;

import com.zenika.zenfoot.dao.GameDao;
import com.zenika.zenfoot.model.Game;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.zenika.zenfoot.dao.mock.MockUtil.matchs;
import static com.zenika.zenfoot.dao.mock.MockUtil.persist;

public class MockGameDao implements GameDao {
    public List<Game> find() {
        return matchs();
    }

    public List<Game> findPast() {
        List<Game> past = new ArrayList<Game>();
        for (Game match : matchs()) {
            if (now().after(match.getKickoff()) && match.hasGoalsSet()) {
                past.add(match);
            }
        }
        return past;
    }

    public List<Game> findRunning() {
        List<Game> running = new ArrayList<Game>();
        for (Game match : matchs()) {
            if (now().after(match.getKickoff()) && !match.hasGoalsSet()) {
                running.add(match);
            }
        }
        return running;
    }

    public List<Game> findIncoming() {
        List<Game> running = new ArrayList<Game>();
        for (Game match : matchs()) {
            if (now().before(match.getKickoff())) {
                running.add(match);
            }
        }
        return running;
    }

    public Game save(Game model) {
        if (!matchs().contains(model)) {
            matchs().add(model);
        }
        persist();
        return model;
    }

    public void delete(Game model) {
        matchs().remove(model);
        persist();
    }

    private Date now() {
        return new Date();
    }
}
