package com.zenika.zenfoot.dao.mock;

import com.zenika.zenfoot.dao.BetDao;
import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.Player;
import java.util.ArrayList;
import java.util.List;
import static com.zenika.zenfoot.dao.mock.MockUtil.bets;
import static com.zenika.zenfoot.dao.mock.MockUtil.persist;

public class MockBetDao implements BetDao {
    public List<Bet> find() {
        return bets();
    }

    public void createOrUpdate(Player user, Match match, int goalsForTeam1, int goalsForTeam2) {
        System.out.println("createOrUpdate " + user + " : " + match + " : " + goalsForTeam1 + " - " + goalsForTeam2);
        Bet bet = find(user, match);
        if (bet != null) {
            bet.setGoals(goalsForTeam1, goalsForTeam2);
//        } else {
//            bet = new Bet(user, match, goalsForTeam1, goalsForTeam2);
        }
        save(bet);
    }

    public Bet find(Player user, Match match) {
        if (user == null || match == null) {
            return null;
        }
        for (Bet bet : bets()) {
//            if (user.equals(bet.getUserId()) && match.equals(bet.getGameId())) {
                return bet;
//            }
        }
        return null;
    }

    public void save(Bet bet) {
        if (!bets().contains(bet)) {
            bets().add(bet);
        }
        persist();
    }

    public void delete(Bet model) {
        bets().remove(model);
        persist();
    }

    public List<Bet> findAll(Match match) {
        List<Bet> bets = new ArrayList<Bet>();
        for (Bet bet : bets()) {
//            if (bet.getGameId().equals(match)) {
                bets.add(bet);
//            }
        }
        return bets;
    }

    public List<Bet> find(Player user) {
        List<Bet> bets = new ArrayList<Bet>();
        for (Bet bet : bets()) {
//            if (bet.getUserId().equals(user)) {
                bets.add(bet);
//            }
        }
        return bets;
    }

    @Override
    public Bet findOrCreate(Player user, Match match) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Bet> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Bet load(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Bet> find(Match match) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
