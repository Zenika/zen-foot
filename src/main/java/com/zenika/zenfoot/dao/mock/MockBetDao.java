package com.zenika.zenfoot.dao.mock;

import com.zenika.zenfoot.dao.BetDao;
import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.User;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MockBetDao implements BetDao {
    private List<Bet> bets = new ArrayList<Bet>();

    public List<Bet> find() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Bet createOrUpdate(User user, Match match, int goalsForTeam1, int goalsForTeam2) {
        System.out.println("createOrUpdate " + user + " : " + match + " : " + goalsForTeam1 + " - " + goalsForTeam2);
        Bet bet = find(user, match);
        if (bet != null) {
            bet.setGoals(goalsForTeam1, goalsForTeam2);
        } else {
            bet = new Bet(user, match, goalsForTeam1, goalsForTeam2);
        }
        return save(bet);
    }

    public Bet find(User user, Match match) {
        unser();
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
        ser();
        return bet;
    }
    
    private void ser() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("/tmp/zenfoot/bets"));
            out.writeObject(bets);
            out.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage()+" ! CREATE DIRECTORY /tmp/zenfoot MANUALLY for it to work!");
        }
    }

    private void unser() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("/tmp/zenfoot/bets"));
            bets = (List<Bet>) in.readObject();
            in.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage()+" ! CREATE DIRECTORY /tmp/zenfoot MANUALLY for it to work!");
        }
    }
}
