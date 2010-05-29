package com.zenika.zenfoot.dao.hibernate;

import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.model.User;
import java.util.List;

public class HibernateUserDao implements UserDao {
    public List<User> find() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public User save(User model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<User> findPending() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void accept(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void reject(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void delete(User model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public User get(String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
