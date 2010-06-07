package com.zenika.zenfoot.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import com.zenika.zenfoot.dao.BetDao;
import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Game;
import com.zenika.zenfoot.model.User;

public class HibernateBetDao extends HibernateDao<Bet> implements BetDao {

	public Bet createOrUpdate(User currentUser, Game match, int goalsForTeam1,
			int goalsForTeam2) {
		Bet bet = new Bet(currentUser, match, goalsForTeam1, goalsForTeam2);
		save(bet);
		return bet;
	}

	public Bet find(User user, Game match) {
		if (user == null)
			return null;
		if (match == null)
			return null;
		Query query = getSession().createQuery("from Bet where userid=? and gameid=?");
		query.setLong(0, user.getId());
		query.setLong(1, match.getId());
		return (Bet) query.uniqueResult();
	}

	public List<Bet> find() {
		return getSession().createQuery("from Bet").list();
	}

	@Override
	public List<Bet> find(User user) {
		Query query = getSession().createQuery("from Bet where userid=?");
		query.setLong(0, user.getId());
		return query.list();
	}

	@Override
	public List<Bet> findAll(Game match) {
		Query query = getSession().createQuery("from Bet where gameid=?");
		query.setLong(0, match.getId());
		return query.list();
	}

}
