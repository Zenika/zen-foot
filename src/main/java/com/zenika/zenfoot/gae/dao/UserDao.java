package com.zenika.zenfoot.gae.dao;

import com.zenika.zenfoot.user.User;

import java.util.List;

/**
 * Created by raphael on 23/04/14.
 */
public interface UserDao {

    void addUser(User user);

    User getUser(String email);

    void deleteUser(String email);

    List<User> getAll();

}
