package com.zenika.zenfoot.gae.services;

import com.google.common.base.Optional;
import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.dao.UserDao;
import com.zenika.zenfoot.user.User;
import restx.factory.Component;
import restx.factory.Factory;

import java.util.HashMap;
import java.util.Map;

@Component
public class MockZenFootUserRepository implements ZenFootUserRepository {

    private Map<String, Optional<User>> users;
    private UserDao userDao;

    public MockZenFootUserRepository() {
        this.users = new HashMap<>();
        this.userDao = Factory.getInstance().getComponent(UserDao.class);
    }
/*
    @Override
    public Optional<User> findUserByName(String email) {

        if (email != null) {
            Optional<User> userOptional = this.users.get(email);
            if (userOptional != null) {
                return userOptional;
            }
            return Optional.absent();
        } else {
            return Optional.absent();
        }
    }
    */

    @Override
    public Optional<User> findUserByName(String email) {
        if (email != null) {
            User user = this.userDao.getUser(email);
            return Optional.fromNullable(user);
        } else {
            return Optional.absent();
        }
    }

    @Override
    public Optional<String> findCredentialByUserName(String name) {
        throw new UnsupportedOperationException();
    }

    /*
    @Override
    public Optional<String> findCredentialByUserName(String email) {
        System.out.println("------------EMAIL de la personne cherchant à s'ID----------");
        System.out.println(email);

        Optional<User> user = findUserByName(email);
        System.out.println("---------------L'utilisateur est présent : ");
        System.out.println(user.isPresent());
        if (!user.isPresent()) {
            return Optional.absent();
        } else {
            return Optional.fromNullable(user.get().getPasswordHash());
        }
    }
    */

    public User getUserbyEmail(String email) {
        return userDao.getUser(email);
    }

    @Override
    public boolean isAdminDefined() {
        return false;
    }

    @Override
    public User defaultAdmin() {
        return this.users.get("raphael.martignoni@zenika.com").get();
    }

    // ///////////////////////////////////////////////////////
    // repo update methods
    // ///////////////////////////////////////////////////////
    public Key<User> createUser(User user) {

        return this.userDao.addUser(user);
    }

    public User updateUser(User user) {
        this.userDao.addUser(user);
        return user;
    }

    public Iterable<User> findAllUsers() {
        return this.userDao.getAll();
    }

    public void deleteUser(String userRef) {
        this.userDao.deleteUser(userRef);
    }


    @Override
    public User get(Key<User> keyUser) {
        return userDao.getUser(keyUser);
    }
}
