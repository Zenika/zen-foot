package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.user.User;
import restx.factory.Component;
import restx.security.RestxSession;

import java.security.Principal;

/**
 * Created by raphael on 24/04/14.
 */
@Component
public class SessionInfo {


    public SessionInfo() {
    }

    public User getUser() {

        RestxSession session = RestxSession.current();
        if (session == null || session.getPrincipal() == null) return null;

        Principal principal = session.getPrincipal().get();
        User user = null;
        if (principal != null) {
            user = (User) principal;
        }
        return user;

    }


}
