package com.zenika.zenfoot.dao.hibernate;

import com.zenika.zenfoot.model.Team;
import java.util.Date;
import java.util.List;


import com.zenika.zenfoot.dao.MatchDao;
import com.zenika.zenfoot.model.Match;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

public class HibernateMatchDao extends HibernateDao<Match> implements MatchDao {

    public HibernateMatchDao() {
        super(Match.class);
    }

    @Override
    public List<Match> findIncoming() {
        return getSession().createCriteria(Match.class).
                add(Restrictions.gt("kickoff", now())).
                addOrder(Order.asc("kickoff")).
                list();
    }

    @Override
    public List<Match> findPast() {
        return getSession().createCriteria(Match.class).
                add(Restrictions.le("kickoff", now())).
                add(Restrictions.ge("goalsForTeam1", 0)).
                add(Restrictions.ge("goalsForTeam2", 0)).
                addOrder(Order.desc("kickoff")).
                list();
    }

    @Override
    public List<Match> findRunning() {
        return getSession().createCriteria(Match.class).
                add(Restrictions.le("kickoff", now())).
                add(Restrictions.or(Restrictions.lt("goalsForTeam1", 0), Restrictions.lt("goalsForTeam2", 0))).
                list();
    }

    private Date now() {
        return new Date();
    }

    @Override
    public int count(Team team) {
        Criteria criteria = getSession().createCriteria(Match.class).
                add(Restrictions.or(Restrictions.eq("team1", team), Restrictions.eq("team2", team)));
        criteria.setProjection(Projections.rowCount());
        return (Integer) criteria.uniqueResult();
    }
}
