package com.zenika.zenfoot.gae.dao;


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

    public static final String matchCacheKey = "matchList";
    protected Logger logger = Logger.getLogger(MatchDAOImpl.class.getName());

    @Override
    public void createUpdate(Match match) {
        OfyService.ofy().save().entity(match).now();
        List<Match> matches = OfyService.ofy().load().type(Match.class).list();

        //Registering in cache
        Cache cache=null;
        try{
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache = cacheFactory.createCache(Collections.emptyMap());
        }
        catch(CacheException e){
           e.printStackTrace();
        }

        if(cache!=null){
            //The object added to the cache must be serializable
            ArrayList<Match> serializable = new ArrayList<>();
            serializable.addAll(matches);

            cache.put(matchCacheKey,serializable);
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

        //Creating cache
        Cache cache = null;
        try{
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache = cacheFactory.createCache(Collections.emptyMap());
        }
        catch(CacheException e){
            e.printStackTrace();
        }

        //Retrieving value
        if(cache!=null){
           cachedValue = (List<Match>)cache.get(matchCacheKey);
        }

        //checking if the value exists in the cache
        if(cachedValue!=null){
            logger.log(Level.INFO,"returning cached value "+cachedValue);
            return cachedValue;
        }
        return OfyService.ofy().load().type(Match.class).list();
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
