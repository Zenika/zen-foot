package com.zenika.zenfoot.gae.dao;

import com.zenika.zenfoot.gae.GenericDAO;
import com.zenika.zenfoot.gae.model.User;


/**
 * Created by raphael on 23/04/14.
 */
public class UserDAOImpl extends GenericDAO<User> implements UserDAO {

    @Override
    public User getUser(String email) {
        User toRet = OfyService.ofy().load().type(User.class).id(email).now();
        return toRet;
    }

}
