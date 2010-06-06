package com.zenika.zenfoot.dao.hibernate;

import java.util.List;

import com.zenika.zenfoot.dao.TeamDao;
import com.zenika.zenfoot.model.Team;

public class HibernateTeamDao extends HibernateDao<Team> implements TeamDao {

	public List<Team> find() {
		return getSession().createQuery("from Team").list();
	}


}
