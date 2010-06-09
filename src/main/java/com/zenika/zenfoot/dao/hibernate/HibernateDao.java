package com.zenika.zenfoot.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zenika.zenfoot.dao.BaseDao;
import com.zenika.zenfoot.model.AbstractModel;
import java.util.List;
import org.hibernate.Criteria;

public abstract class HibernateDao<T extends AbstractModel> implements BaseDao<T> {

    private Class<T> modelClass;
    private SessionFactory sessionFactory;

    public HibernateDao(Class<T> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public void delete(T model) {
        getSession().delete(model);
    }

    @Override
    public T load(long id) {
        return (T) getSession().get(modelClass, id);
    }

    @Override
    public void save(T model) {
        getSession().saveOrUpdate(model);
    }

    @Override
    public List<T> findAll() {
        Criteria criteria = getSession().createCriteria(modelClass);
        return (List<T>) criteria.list();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
