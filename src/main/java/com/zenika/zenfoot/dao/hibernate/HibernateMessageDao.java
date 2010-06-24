package com.zenika.zenfoot.dao.hibernate;

import com.zenika.zenfoot.dao.MessageDao;
import com.zenika.zenfoot.model.Message;
import java.util.List;
import org.hibernate.criterion.Order;

public class HibernateMessageDao extends HibernateDao<Message> implements MessageDao {

    public HibernateMessageDao() {
        super(Message.class);
    }

    @Override
    public List<Message> findLast(int nbMessages) {
        return getSession().createCriteria(Message.class).addOrder(Order.desc("datetime")).setMaxResults(nbMessages).list();
    }

    @Override
    public Message findLastOne() {
        return (Message) getSession().createCriteria(Message.class).addOrder(Order.desc("datetime")).setMaxResults(1).uniqueResult();
    }
}
