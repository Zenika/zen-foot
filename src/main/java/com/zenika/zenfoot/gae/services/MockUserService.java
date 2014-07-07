package com.zenika.zenfoot.gae.services;

import com.google.common.base.Optional;
import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.exception.JsonWrappedErrorWebException;
import com.zenika.zenfoot.gae.utils.PasswordUtils;
import com.zenika.zenfoot.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.security.UserService;

public class MockUserService implements UserService<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockUserService.class);

    private final MockZenFootUserRepository zenFootUserRepository;

    public MockUserService(MockZenFootUserRepository zenFootUserRepository) {
        this.zenFootUserRepository = zenFootUserRepository;
    }

    @Override
    public Optional<User> findUserByName(String name) {
        if (name == null) System.out.println("----------NAME IS NULL----------");
        return zenFootUserRepository.findUserByName(name);
    }

    @Override
    public Optional<User> findAndCheckCredentials(String email, String passwordHash) {
        Optional<User> optionalUser = zenFootUserRepository.findUserByName(email);

        if (!optionalUser.isPresent()) {
            return optionalUser;
        }

        String credentials = optionalUser.get().getPasswordHash();

        if (credentials.equals(PasswordUtils.getPasswordHash(passwordHash))) {
            return optionalUser;
        } else {
            return Optional.absent();
        }
    }

    public Key<User> createUser(User user) {
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

    public void resetPWD(String userEmail,String oldPW, String newPW) {
        Optional<User> userOpt = this.findAndCheckCredentials(userEmail, oldPW);
        if(!userOpt.isPresent()){
            throw new JsonWrappedErrorWebException("WRONG_PWD","le mot de passe renseigné n'est pas le bon");
        }
        else{
            User user = userOpt.get();
            user.setPassword(newPW);
            this.createUser(user);
        }
    }
}
