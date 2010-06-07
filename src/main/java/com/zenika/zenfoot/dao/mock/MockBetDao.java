package com.zenika.zenfoot.dao.mock;

import com.zenika.zenfoot.dao.BetDao;
import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Game;
import com.zenika.zenfoot.model.User;
import java.util.ArrayList;
import java.util.List;
import static com.zenika.zenfoot.dao.mock.MockUtil.bets;
import static com.zenika.zenfoot.dao.mock.MockUtil.persist;

public class MockBetDao implements BetDao {
    public List<Bet> find() {
        return bets();
    }

    public Bet createOrUpdate(User user, Game match, int goalsForTeam1, int goalsForTeam2) {
        System.out.println("createOrUpdate " + user + " : " + match + " : " + goalsForTeam1 + " - " + goalsForTeam2);
        Bet bet = find(user, match);
        if (bet != null) {
            bet.setGoals(goalsForTeam1, goalsForTeam2);
        } else {
            bet = new Bet(user, match, goalsForTeam1, goalsForTeam2);
        }
        return save(bet);
    }

    public Bet find(User user, Game match) {
        if (user == null || match == null) {
            return null;
        }
        for (Bet bet : bets()) {
            if (user.equals(bet.getUser()) && match.equals(bet.getGame())) {
                return bet;
            }
        }
        return null;
    }

    public Bet save(Bet bet) {
        if (!bets().contains(bet)) {
            bets().add(bet);
        }
        persist();
        return bet;
    }

    public void delete(Bet model) {
        bets().remove(model);
        persist();
    }

    public List<Bet> findAll(Game match) {
        List<Bet> bets = new ArrayList<Bet>();
        for (Bet bet : bets()) {
            if (bet.getGame().equals(match)) {
                bets.add(bet);
            }
        }
        return bets;
    }

    public List<Bet> find(User user) {
        List<Bet> bets = new ArrayList<Bet>();
        for (Bet bet : bets()) {
            if (bet.getUser().equals(user)) {
                bets.add(bet);
            }
        }
        return bets;
    }
}
