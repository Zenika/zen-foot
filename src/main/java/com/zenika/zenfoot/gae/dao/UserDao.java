package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.user.User;

import java.util.List;

/**
 * Created by raphael on 23/04/14.
 */
public interface UserDao {

    Key<User> addUser(User user);

    User getUser(String email);

    User getUser(Key<User> key);

    void deleteUser(String email);

    List<User> getAll();

}
