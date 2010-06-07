package com.zenika.zenfoot.dao.hibernate;

import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.model.User;
import java.util.List;

import org.hibernate.Query;

public class HibernateUserDao extends HibernateDao<User> implements UserDao {
	public List<User> find() {
		return getSession().createQuery("from User").list();
	}

	public List<User> findPending() {
		return getSession().createQuery("from User where pending = ?")
				.setBoolean(0, true).list();
	}

	public void accept(User user) {
		user.setPending(true);
		getSession().saveOrUpdate(user);
	}

	public void reject(User user) {
		user.setPending(false);
		getSession().saveOrUpdate(user);
	}

	public User get(String email) {
		Query query = getSession().createQuery("from User where email=?");
		query.setString(0, email);
		return (User) query.uniqueResult();
	}
}
