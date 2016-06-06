package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.ObjectifyService;
import com.zenika.zenfoot.gae.GenericDAO;
import com.zenika.zenfoot.gae.model.User;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Created by raphael on 23/04/14.
 */
public class UserDAOImpl extends GenericDAO<User> implements UserDAO {

    @Override
    public User getUser(String email) {
        User toRet = ObjectifyService.ofy().load().type(User.class).id(email).now();
        return toRet;
    }

    @Override
    public List<User> getAll(@NotNull String name) {
        return ObjectifyService.ofy().load().type(User.class)
                .filter("lastName >=", name)
                .filter("lastName <", name + "\ufffd").list();
    }

}
