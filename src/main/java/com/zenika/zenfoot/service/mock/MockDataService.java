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
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.math.NumberUtils;

public class MockDataService implements DataService {
    private transient UserDao userDao = new MockUserDao();
    private transient MatchDao matchDao = new MockMatchDao();
    private transient BetDao betDao = new MockBetDao();

    public void registerUser(String email, String password) {
        userDao.save(new User(email, DigestUtils.md5Hex(password)));
    }

    public void updateGoalsForMatch(Match match) {
        matchDao.save(match);
        System.out.println("saving match " + match);
        if (match.hasGoalsSet()) {
            System.out.println("match has goals set");
            List<Bet> bets = betDao.findAll(match);
            System.out.println("found bets " + bets.size());
            for (Bet bet : bets) {
                updateUserPoints(bet, match);
            }
        }
    }

    private void updateUserPoints(Bet bet, Match match) {
        System.out.println("update user points " + bet + ", " + match);
        User user = bet.getUser();
        if (bet.isBetSet()) {
            System.out.println("bet is set :: BET(" + bet.getGoalsForTeam1() + "," + bet.getGoalsForTeam2() + ") <> MATCH(" + match.getGoalsForTeam1() + "," + match.getGoalsForTeam2() + ")");
            System.out.println("comp::" + NumberUtils.compare(bet.getGoalsForTeam1(), match.getGoalsForTeam1()) + "::" + NumberUtils.compare(bet.getGoalsForTeam2(), match.getGoalsForTeam2()));
            if (bet.getGoalsForTeam1() == match.getGoalsForTeam1() && bet.getGoalsForTeam2() == match.getGoalsForTeam2()) {
                System.out.println(user.getPoints() + "+3pts");
                user.setPoints(user.getPoints() + 3);
                userDao.save(user);
            } else if (NumberUtils.compare(bet.getGoalsForTeam1(), bet.getGoalsForTeam2()) == NumberUtils.compare(match.getGoalsForTeam1(), match.getGoalsForTeam2())) {
                System.out.println(user.getPoints() + "+1pt");
                user.setPoints(user.getPoints() + 1);
                userDao.save(user);
            }
        }
    }
}
