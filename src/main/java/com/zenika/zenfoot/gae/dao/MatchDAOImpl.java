package com.zenika.zenfoot.gae.dao;


import com.google.appengine.repackaged.com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.zenika.zenfoot.gae.GenericDAO;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.utils.KeyBuilder;
import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatchDAOImpl extends GenericDAO<Match> implements MatchDAO {


    @Override
    public Match get(Long id, Event event) {
        return ObjectifyService.ofy().load().key(
                KeyBuilder.buildMatchKey(id, event.getId())).now();
    }
}
