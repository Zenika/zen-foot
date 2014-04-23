package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Objectify;
import com.zenika.zenfoot.user.User;

import java.util.List;

/**
 * Created by raphael on 23/04/14.
 */
public class UserDAOImpl implements UserDao {

    public static Objectify ofy = OfyService.ofy();

    public UserDAOImpl() {

    };

    @Override
    public void addUser(User user) {
        ofy.save().entity(user).now();
    }

    @Override
    public User getUser(String email) {
        User toRet = ofy.load().type(User.class).id(email).now();
        return toRet;
    }

    @Override
    public void deleteUser(String email) {
        ofy.delete().type(User.class).id(email).now();
    }

    @Override
    public List<User> getAll() {
        return ofy.load().type(User.class).list();
    }
}
