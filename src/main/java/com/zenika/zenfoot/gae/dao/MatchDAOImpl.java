package com.zenika.zenfoot.gae.dao;


import com.google.appengine.repackaged.com.google.common.collect.Lists;
import com.zenika.zenfoot.gae.model.Match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

/**
 * Created by raphael on 29/04/14.
 */
public class MatchDAOImpl implements MatchDAO {

    public static final String MatchCacheKey = "matchList";
    private Logger logger = Logger.getLogger(getClass().getName());
    private Cache cache=null;

    public MatchDAOImpl(){
        try {
            this.cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
        } catch (CacheException e) {

        }
    }

    @Override
    public void createUpdate(Match match) {
        OfyService.ofy().save().entity(match).now();
        List<Match> matches = OfyService.ofy().load().type(Match.class).list();

        if(cache!=null){
            //Removing cached value
            cache.put(MatchCacheKey,null);
        }
    }


    @Override
    public Match getMatch(Long id) {
        return OfyService.ofy().load().type(Match.class).id(id).now();
    }

    @Override
    public void deleteMatch(Long id) {
        OfyService.ofy().delete().type(Match.class).id(id).now();
    }

    @Override
    public List<Match> getAll() {
        List<Match> cachedValue=null;

        //Retrieving value
        if(cache!=null){
           cachedValue = (List<Match>)cache.get(MatchCacheKey);
        }

        //checking if the value exists in the cache
        if(cachedValue!=null){
            logger.log(Level.INFO,"returning cached value "+cachedValue);
            return cachedValue;
        }
        else{
            //if not, matchs are fetched in the database and added to the cache
            List<Match> matchs = OfyService.ofy().load().type(Match.class).list();
            if(cache!=null){
                cache.put(MatchCacheKey, Lists.newArrayList(matchs));
            }
            return matchs;
        }
    }

    @Override
    public void deleteAll() {
        Logger logger = Logger.getLogger(MatchDAOImpl.class.getName());
        List<Match> matchs = getAll();
        for (Match match : matchs) {
            this.deleteMatch(match.getId());
        }
        logger.log(Level.WARNING, "deleted " + matchs.size() + " matchs");
    }


}
