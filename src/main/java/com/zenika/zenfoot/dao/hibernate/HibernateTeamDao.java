package com.zenika.zenfoot.dao.hibernate;

import java.util.List;

import com.zenika.zenfoot.dao.TeamDao;
import com.zenika.zenfoot.model.Team;
import org.hibernate.criterion.Restrictions;

public class HibernateTeamDao extends HibernateDao<Team> implements TeamDao {

    @Override
    public List<Team> find() {
        return getSession().createCriteria(Team.class).list();
    }

    @Override
    public Team findOrCreate(Team team) {
        Team t = (Team) getSession().createCriteria(Team.class).add(Restrictions.eq("name", team.getName())).uniqueResult();
        if (t == null) {
            save(new Team(team.getName(), team.getImageName()));
        }
        return t;
    }
}
