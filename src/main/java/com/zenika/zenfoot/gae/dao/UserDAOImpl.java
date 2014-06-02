package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Objectify;
import com.zenika.zenfoot.user.User;

import java.util.List;

/**
 * Created by raphael on 23/04/14.
 */
public class UserDAOImpl implements UserDao {


    public UserDAOImpl() {

    };

    @Override
    public void addUser(User user) {
        OfyService.ofy().save().entity(user).now();
    }

    @Override
    public User getUser(String email) {
        User toRet = OfyService.ofy().load().type(User.class).id(email).now();
        return toRet;
    }

    @Override
    public void deleteUser(String email) {
        OfyService.ofy().delete().type(User.class).id(email).now();
    }

    @Override
    public List<User> getAll() {
        return OfyService.ofy().load().type(User.class).list();
    }
}
