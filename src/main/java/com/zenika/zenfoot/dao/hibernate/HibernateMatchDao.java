package com.zenika.zenfoot.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.zenika.zenfoot.dao.MatchDao;
import com.zenika.zenfoot.model.Match;

public class HibernateMatchDao extends HibernateDao<Match> implements MatchDao {

	public List<Match> findIncoming() {
		Query query = getSession().createQuery("from Match where kickoff > :now");
		query.setParameter("now", now());
		return query.list();
	}

	public List<Match> findPast() {
		Query query = getSession().createQuery("from Match where kickoff < ?");
		query.setDate(0, afterMatch());
		return query.list();
	}

	public List<Match> findRunning() {
		Query query = getSession().createQuery("from Match where kickoff < ? and ? < kickoff");
		query.setDate(0, now());
		query.setDate(1, afterMatch());
		return query.list();
	}

	public List<Match> find() {
		return getSession().createQuery("from Match").list();
	}
	
	private Date beforeMatch(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 2);
		return cal.getTime();
	}
	
	private Date afterMatch(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -2);
		return cal.getTime();
	}

    private Date now() {
        return new Date();
    }

}
