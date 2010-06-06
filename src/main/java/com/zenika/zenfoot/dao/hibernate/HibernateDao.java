package com.zenika.zenfoot.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zenika.zenfoot.dao.BaseDao;


public abstract class HibernateDao<T> implements BaseDao<T>{
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public Session getSession(){
		if ( sessionFactory.getCurrentSession() == null )
			return sessionFactory.openSession();
		return sessionFactory.getCurrentSession();
	}
	
	public T save(T model){
		getSession().saveOrUpdate(model);
		return model;
	}

	public void delete(T model) {
		getSession().delete(model);
	}

}
