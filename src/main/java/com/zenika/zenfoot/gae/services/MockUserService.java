package com.zenika.zenfoot.gae.services;

import com.google.common.base.Optional;
import com.googlecode.objectify.Key;
import com.zenika.zenfoot.user.User;
import restx.security.UserService;

public class MockUserService implements UserService<User>{

    private final MockZenFootUserRepository zenFootUserRepository;

    public MockUserService(MockZenFootUserRepository zenFootUserRepository){
        this.zenFootUserRepository = zenFootUserRepository;
    }

    @Override
    public Optional<User> findUserByName(String name) {
        if(name==null)System.out.println("----------NAME IS NULL----------");
        return zenFootUserRepository.findUserByName(name);
    }

    @Override
    public Optional<User> findAndCheckCredentials(String email, String passwordHash) {

        Optional<User> toRet = zenFootUserRepository.findUserByName(email);
        if(!toRet.isPresent()) return toRet;

        Optional<String> credentials = zenFootUserRepository.findCredentialByUserName(email);
        if(!credentials.isPresent()) return Optional.absent();

        boolean returns = credentials.get().equals(passwordHash);
        if(returns){
            return toRet;
        }
        else{
            return Optional.absent();
        }
    }

    public Key<User> createUser(User user) {
        return zenFootUserRepository.createUser(user);
    }

    public User get(Key<User> keyUser) {
        return zenFootUserRepository.get(keyUser);
    }
}
