package com.zenika.zenfoot.dao.hibernate;

import com.zenika.zenfoot.dao.PlayerDao;
import com.zenika.zenfoot.model.Player;
import java.util.List;
import org.hibernate.criterion.Order;

import org.hibernate.criterion.Restrictions;

public class HibernatePlayerDao extends HibernateDao<Player> implements PlayerDao {

    public HibernatePlayerDao() {
        super(Player.class);
    }

    @Override
    public List<Player> findActive() {
        return getSession().createCriteria(Player.class).add(Restrictions.eq("pending", false)).addOrder(Order.desc("points")).list();
    }

    @Override
    public List<Player> findPending() {
        return getSession().createCriteria(Player.class).add(Restrictions.eq("pending", true)).list();
    }

    @Override
    public void accept(Player user) {
        user.setPending(false);
        getSession().saveOrUpdate(user);
    }

    @Override
    public void reject(Player user) {
        user.setPending(true);
        getSession().saveOrUpdate(user);
    }

    @Override
    public Player find(String email) {
        return (Player) getSession().createCriteria(Player.class).add(Restrictions.naturalId().set("email", email)).uniqueResult();
    }
}
