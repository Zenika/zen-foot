package com.zenika.zenfoot.gae.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import restx.security.UserService;

import com.google.common.hash.Hashing;
import com.google.common.base.Optional;
import com.googlecode.objectify.Key;
import com.zenika.zenfoot.user.User;

import java.util.logging.Level;

public class MockUserService implements UserService<User>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MockUserService.class);

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

        boolean returns = credentials.get().equals(getPasswordHash(passwordHash));
        if(returns){
            return toRet;
        }
        else{
            return Optional.absent();
        }
    }

    public Key<User> createUser(User user) {

            String password = user.getPasswordHash();
            user.setPasswordHash(getPasswordHash(password));
        return zenFootUserRepository.createUser(user);
    }

    public User get(Key<User> keyUser) {
        return zenFootUserRepository.get(keyUser);
    }
    
    public User getUserByEmail(String email) {
    	return zenFootUserRepository.getUserbyEmail(email);
    }
    
    public User updateUser(User user) {
    	return zenFootUserRepository.updateUser(user);
    }
    
    private String getPasswordHash(String password) {
    	String passwordHash = Hashing.sha256()
    			.hashUnencodedChars(password)
    			.toString();
    	
    	LOGGER.debug("Password \"{}\" hashed into \"{}\"", password, passwordHash);
    	
    	return passwordHash;
    }
    
}
