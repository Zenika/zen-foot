package com.zenika.zenfoot.service;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zenika.zenfoot.dao.BetDao;
import com.zenika.zenfoot.dao.MatchDao;
import com.zenika.zenfoot.dao.PlayerDao;
import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.Player;

public class DefaultDataService implements DataService {

    Logger logger = LoggerFactory.getLogger(DefaultDataService.class);
    private PlayerDao playerDao;
    private BetDao betDao;
    private MatchDao matchDao;

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
        playerDao.save(player);
    }

    @Override
    public void updateUserPoints() {
        for (Player user : playerDao.findAll()) {
            updateUserPoints(user);
        }
    }

    public int computePoints(Bet bet, Match match) {
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
    public Match saveMatch(Match match, int goalsForTeam1, int goalsForTeam2, String comments) {
        Match m = matchDao.load(match.getId());
        m.setGoalsForTeam1(goalsForTeam1);
        m.setGoalsForTeam2(goalsForTeam2);
        m.setComments(comments);
        matchDao.save(m);
        updateUserPoints();
        return m;
    }

    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public PlayerDao getPlayerDao() {
        return playerDao;
    }

    public void setBetDao(BetDao betDao) {
        this.betDao = betDao;
    }

    public BetDao getBetDao() {
        return betDao;
    }

    public MatchDao getMatchDao() {
        return matchDao;
    }

    public void setMatchDao(MatchDao matchDao) {
        this.matchDao = matchDao;
    }
}
