package com.zenika.zenfoot.service;

import org.apache.commons.lang.math.NumberUtils;

import com.zenika.zenfoot.dao.BetDao;
import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.User;

public class DefaultDataService implements DataService {
//    private transient UserDao userDao = new MockUserDao();
//    private transient BetDao betDao = new MockBetDao();
    private UserDao userDao;
    private BetDao betDao;

    @Override
    public void updateUserPoints(User user) {
        int points = 0;
        for (Bet bet : getBetDao().find(user)) {
            if (bet.isBetSet() && bet.getMatch().hasGoalsSet()) {
                System.out.println(bet + " has goals set");
                points += computePoints(bet, bet.getMatch());
            }
        }
        user.setPoints(points);
        getUserDao().save(user);
    }

    @Override
    public void updateUserPoints() {
        for (User user : getUserDao().find()) {
            updateUserPoints(user);
        }
    }

    private int computePoints(Bet bet, Match match) {
        System.out.println("bet is set :: BET(" + bet.getGoalsForTeam1() + "," + bet.getGoalsForTeam2() + ") <> MATCH(" + match.getGoalsForTeam1() + "," + match.getGoalsForTeam2() + ") :: comp::" + NumberUtils.compare(bet.getGoalsForTeam1(), match.getGoalsForTeam1()) + "::" + NumberUtils.compare(bet.getGoalsForTeam2(), match.getGoalsForTeam2()));
        if (bet.getGoalsForTeam1() == match.getGoalsForTeam1() && bet.getGoalsForTeam2() == match.getGoalsForTeam2()) {
            System.out.println("+3pts");
            return 3;
        } else if (NumberUtils.compare(bet.getGoalsForTeam1(), bet.getGoalsForTeam2()) == NumberUtils.compare(match.getGoalsForTeam1(), match.getGoalsForTeam2())) {
            System.out.println("+1pt");
            return 1;
        }
        return 0;
    }

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setBetDao(BetDao betDao) {
		this.betDao = betDao;
	}

	public BetDao getBetDao() {
		return betDao;
	}
}
