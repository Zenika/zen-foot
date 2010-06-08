package com.zenika.zenfoot.dao.hibernate;

import java.util.Date;
import java.util.List;


import com.zenika.zenfoot.dao.GameDao;
import com.zenika.zenfoot.model.Match;
import org.hibernate.criterion.Restrictions;

public class HibernateGameDao extends HibernateDao<Match> implements GameDao {

    @Override
    public List<Match> findIncoming() {
        return getSession().createCriteria(Match.class).add(Restrictions.gt("kickoff", now())).list();
    }

    @Override
    public List<Match> findPast() {
        return getSession().createCriteria(Match.class).
                add(Restrictions.le("kickoff", now())).
                add(Restrictions.ge("goalsForTeam1", 0)).
                add(Restrictions.ge("goalsForTeam2", 0)).
                list();
    }

    @Override
    public List<Match> findRunning() {
        return getSession().createCriteria(Match.class).
                add(Restrictions.le("kickoff", now())).
                add(Restrictions.or(Restrictions.lt("goalsForTeam1", 0), Restrictions.lt("goalsForTeam2", 0))).
                list();
    }

    @Override
    public List<Match> find() {
        return getSession().createCriteria(Match.class).list();
    }

    private Date now() {
        return new Date();
    }

    @Override
    public Match get(Match match) {
        return (Match) getSession().load(Match.class, match.getId());
    }
}
