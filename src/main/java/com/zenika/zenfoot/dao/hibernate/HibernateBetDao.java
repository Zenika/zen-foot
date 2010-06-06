package com.zenika.zenfoot.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import com.zenika.zenfoot.dao.BetDao;
import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.User;

public class HibernateBetDao extends HibernateDao<Bet> implements BetDao {

	public Bet createOrUpdate(User currentUser, Match match, int goalsForTeam1,
			int goalsForTeam2) {
		Bet bet = new Bet(currentUser, match, goalsForTeam1, goalsForTeam2);
		save(bet);
		return bet;
	}

	public Bet find(User user, Match match) {
		Query query = getSession().createQuery("from Bet where userid=? and matchid=?");
		query.setLong(1,user.getId());
		query.setLong(2, match.getId());
		return (Bet) query.uniqueResult();
	}

	public List<Bet> find() {
		return getSession().createQuery("from Bet").list();
	}

	@Override
	public List<Bet> find(User user) {
		Query query = getSession().createQuery("from Bet where userid=?");
		query.setLong(1,user.getId());
		return query.list();
	}

	@Override
	public List<Bet> findAll(Match match) {
		Query query = getSession().createQuery("from Bet where matchid=?");
		query.setLong(1,match.getId());
		return query.list();
	}


}
