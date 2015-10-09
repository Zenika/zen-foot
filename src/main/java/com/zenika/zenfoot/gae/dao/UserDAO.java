package com.zenika.zenfoot.gae.dao;

import com.zenika.zenfoot.gae.IGenericDAO;
import com.zenika.zenfoot.gae.model.User;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Created by raphael on 23/04/14.
 */
public interface UserDAO extends IGenericDAO<User> {

    User getUser(String email);

    /**
     * Get all users with  name strating with parameter supplied.
     * @param name prefix for name to find
     * @return users
     */
    List<User> getAll(@NotNull String name);
}
