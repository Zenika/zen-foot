package com.zenika.zenfoot.dao.hibernate;

import com.zenika.zenfoot.dao.TeamDao;
import com.zenika.zenfoot.model.Team;
import org.hibernate.criterion.Restrictions;

public class HibernateTeamDao extends HibernateDao<Team> implements TeamDao {

    public HibernateTeamDao() {
        super(Team.class);
    }

    @Override
    public Team findOrCreate(Team team) {
        Team t = (Team) getSession().createCriteria(Team.class).add(Restrictions.naturalId().set("name", team.getName())).uniqueResult();
        if (t == null) {
            save(t = team);
        }
        return t;
    }
}
