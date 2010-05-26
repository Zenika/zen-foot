package com.zenika.zenfoot.dao;

import com.zenika.zenfoot.model.User;
import java.util.List;

public interface UserDao extends BaseDao<User> {
    public List<User> findPending();

    public void accept(User user);

    public void reject(User user);
}
