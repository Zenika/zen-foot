package com.zenika.zenfoot.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import com.zenika.zenfoot.dao.BetDao;
import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.Player;

public class HibernateBetDao extends HibernateDao<Bet> implements BetDao {

    @Override
    public Bet find(Player player, Match match) {
        if (player == null) {
            return null;
        }
        if (match == null) {
            return null;
        }
        Query query = getSession().createQuery("from Bet where player.id=? and match.id=?");
        query.setLong(0, player.getId());
        query.setLong(1, match.getId());
        return (Bet) query.uniqueResult();
    }

    @Override
    public List<Bet> find() {
        return getSession().createCriteria(Bet.class).list();
    }

    @Override
    public List<Bet> find(Player user) {
        Query query = getSession().createQuery("from Bet where player.id=?");
        query.setLong(0, user.getId());
        return query.list();
    }

    @Override
    public List<Bet> findAll(Match match) {
        Query query = getSession().createQuery("from Bet where match.id=?");
        query.setLong(0, match.getId());
        return query.list();
    }

    @Override
    public Bet findOrCreate(Player user, Match match) {
        Bet bet = find(user, match);
        if (bet == null) {
            bet = save(new Bet(user, match));
        }
        return bet;
    }
}
