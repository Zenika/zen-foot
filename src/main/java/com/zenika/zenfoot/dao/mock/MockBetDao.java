package com.zenika.zenfoot.dao.mock;

import com.zenika.zenfoot.dao.BetDao;
import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.User;
import java.util.ArrayList;
import java.util.List;

public class MockBetDao implements BetDao {
    private static final ThreadLocal<MockBetDao> dao = new ThreadLocal<MockBetDao>() {
        @Override
        protected MockBetDao initialValue() {
            return new MockBetDao();
        }
    };
    private List<Bet> bets = new ArrayList<Bet>();

    public static MockBetDao get() {
        return dao.get();
    }

    public List<Bet> find() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Bet createOrUpdate(User user, Match match, int goalsForTeam1, int goalsForTeam2) {
        System.out.println("createOrUpdate " + user + " : " + match + " : " + goalsForTeam1 + " - " + goalsForTeam2);
        Bet bet = find(user, match);
        if (bet != null) {
            System.out.println("bet != null");
            bet.setGoals(goalsForTeam1, goalsForTeam2);
        } else {
            System.out.println("bet == null");
            bet = new Bet(user, match, goalsForTeam1, goalsForTeam2);
        }
        System.out.println(bets.size());
        return save(bet);
    }

    public Bet find(User user, Match match) {
        for (Bet bet : bets) {
            if (user.equals(bet.getUser()) && match.equals(bet.getMatch())) {
                return bet;
            }
        }
        return null;
    }

    public Bet save(Bet bet) {
        if (bets.contains(bet)) {
            //
        } else {
            bets.add(bet);
        }
        return bet;
    }
}
