package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.zenika.zenfoot.user.User;

/**
 * Created by raphael on 23/04/14.
 */
public class OfyService {

    static{
        factory().register(User.class);
    }

    public static Objectify ofy(){
        return ObjectifyService.ofy();
    }
    public static ObjectifyFactory factory(){
        return ObjectifyService.factory();
    }
}
