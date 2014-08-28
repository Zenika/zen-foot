package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.zenika.zenfoot.gae.model.GamblerBets;

/**
 * Created by raphael on 28/08/14.
 */
public class GamblerBetsDAO {

    public Key<GamblerBets> save(GamblerBets gamblerBets){
        return ObjectifyService.ofy().save().entity(gamblerBets).now();
    }
}
