package com.zenika.zenfoot.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.zenika.zenfoot.dao.GameDao;
import com.zenika.zenfoot.model.Game;

public class HibernateGameDao extends HibernateDao<Game> implements GameDao {

	public List<Game> findIncoming() {
		Query query = getSession().createQuery("from Game where kickoff > ?");
		query.setParameter(0, now());
		return query.list();
	}

	public List<Game> findPast() {
		Query query = getSession().createQuery("from Game where kickoff < ?");
		query.setDate(0, afterGame());
		return query.list();
	}

	public List<Game> findRunning() {
		Query query = getSession().createQuery("from Game where kickoff < ? and ? < kickoff");
		query.setDate(0, now());
		query.setDate(1, afterGame());
		return query.list();
	}

	public List<Game> find() {
		return getSession().createQuery("from Game").list();
	}
	
	private Date beforeGame(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 2);
		return cal.getTime();
	}
	
	private Date afterGame(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -2);
		return cal.getTime();
	}

    private Date now() {
        return new Date();
    }

}
