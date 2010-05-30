package com.zenika.zenfoot.service.mock;

import com.zenika.zenfoot.dao.BetDao;
import com.zenika.zenfoot.dao.MatchDao;
import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.dao.mock.MockBetDao;
import com.zenika.zenfoot.dao.mock.MockMatchDao;
import com.zenika.zenfoot.dao.mock.MockUserDao;
import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.User;
import com.zenika.zenfoot.service.DataService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.math.NumberUtils;

public class MockDataService implements DataService {
    private transient UserDao userDao = new MockUserDao();
    private transient MatchDao matchDao = new MockMatchDao();
    private transient BetDao betDao = new MockBetDao();

    public void registerUser(String email, String password) {
        userDao.save(new User(email, DigestUtils.md5Hex(password)));
    }

    public void updateUserPoints(User user) {
        int points = 0;
        for (Bet bet : betDao.find(user)) {
            if (bet.isBetSet() && bet.getMatch().hasGoalsSet()) {
                System.out.println(bet + " has goals set");
                points += computePoints(bet, bet.getMatch());
            }
        }
        user.setPoints(points);
        userDao.save(user);
    }

    public void updateUserPoints() {
        for (User user : userDao.find()) {
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
}
