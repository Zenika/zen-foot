package com.zenika.zenfoot.gae.services;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.user.User;
import restx.security.UserRepository;

public interface ZenFootUserRepository extends UserRepository<User> {
    User get(Key<User> keyUser);
}
