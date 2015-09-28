package com.zenika.zenfoot.gae.dao;

import com.zenika.zenfoot.gae.IGenericDAO;
import com.zenika.zenfoot.gae.model.User;


/**
 * Created by raphael on 23/04/14.
 */
public interface UserDAO extends IGenericDAO<User> {

    User getUser(String email);
}
