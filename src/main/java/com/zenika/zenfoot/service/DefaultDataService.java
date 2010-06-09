package com.zenika.zenfoot.service;

import org.apache.commons.lang.math.NumberUtils;

import com.zenika.zenfoot.dao.BetDao;
import com.zenika.zenfoot.dao.GameDao;
import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultDataService implements DataService {

    Logger logger = LoggerFactory.getLogger(DefaultDataService.class);
    private UserDao userDao;
    private BetDao betDao;
    private GameDao matchDao;

    @Override
    public void updateUserPoints(Player player) {
        int points = 0;
        for (Bet bet : betDao.find(player)) {
            if (bet.isBetSet() && bet.getMatch().hasGoalsSet()) {
                if (logger.isDebugEnabled()) {
                    logger.debug(bet + " has goals set");
                }
                points += computePoints(bet, bet.getMatch());
            }
        }
        player.setPoints(points);
        userDao.save(player);
    }

    @Override
    public void updateUserPoints() {
        for (Player user : getUserDao().findAll()) {
            updateUserPoints(user);
        }
    }

    private int computePoints(Bet bet, Match match) {
        if (logger.isDebugEnabled()) {
            logger.debug("bet is set :: BET(" + bet.getGoalsForTeam1() + "," + bet.getGoalsForTeam2() + ") <> MATCH(" + match.getGoalsForTeam1() + "," + match.getGoalsForTeam2() + ") :: comp::" + NumberUtils.compare(bet.getGoalsForTeam1(), match.getGoalsForTeam1()) + "::" + NumberUtils.compare(bet.getGoalsForTeam2(), match.getGoalsForTeam2()));
        }
        if (bet.getGoalsForTeam1() == match.getGoalsForTeam1() && bet.getGoalsForTeam2() == match.getGoalsForTeam2()) {
            if (logger.isDebugEnabled()) {
                logger.debug("+3pts");
            }
            return 3;
        } else if (NumberUtils.compare(bet.getGoalsForTeam1(), bet.getGoalsForTeam2()) == NumberUtils.compare(match.getGoalsForTeam1(), match.getGoalsForTeam2())) {
            if (logger.isDebugEnabled()) {
                logger.debug("+1pt");
            }
            return 1;
        }
        return 0;
    }

    @Override
    public Bet saveBet(Player player, Match match, int goalsForTeam1, int goalsForTeam2) {
        Bet bet = betDao.findOrCreate(player, match);
        bet.setGoalsForTeam1(goalsForTeam1);
        bet.setGoalsForTeam2(goalsForTeam2);
        betDao.save(bet);
        return bet;
    }

    @Override
    public Match saveMatch(Match match, int goalsForTeam1, int goalsForTeam2) {
        Match m = matchDao.load(match.getId());
        m.setGoalsForTeam1(goalsForTeam1);
        m.setGoalsForTeam2(goalsForTeam2);
        matchDao.save(m);
        updateUserPoints();
        return m;
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

    public GameDao getMatchDao() {
        return matchDao;
    }

    public void setMatchDao(GameDao matchDao) {
        this.matchDao = matchDao;
    }
}
