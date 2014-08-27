package com.zenika.zenfoot.gae.dao;


import com.google.appengine.repackaged.com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.model.Match;
import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatchDAOImpl implements MatchDAO {

    public static final String MATCH_LIST_CACHE_KEY = "matchList";

    private Logger logger = Logger.getLogger(getClass().getName());
    private Cache cache = null;

    public MatchDAOImpl() {
        try {
            this.cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
        } catch (CacheException e) {
            logger.log(Level.WARNING, "Cache creation error", e);
        }
    }

    // Cache

    private void clearCache() {
        if (cache != null) {
            cache.remove(MATCH_LIST_CACHE_KEY);
        }
    }

    private Match getMatchFromCache(Long id) {
        List<Match> matchs = getMatchsFromCache();
        if (matchs != null) {
            for (Match match : matchs) {
                if (id.equals(match.getId())) {
                    return match;
                }
            }
        }
        return null;
    }

    private List<Match> getMatchsFromCache() {
        return (cache != null) ? (List<Match>) cache.get(MATCH_LIST_CACHE_KEY) : null;
    }


    // Operations

    @Override
    public void createUpdate(Match match) {
        OfyService.ofy().save().entity(match).now();
        clearCache();
    }

    @Override
    public Match getMatch(Long id) {
        Match matchFromCache = getMatchFromCache(id);
        if (matchFromCache != null) {
            return matchFromCache;
        } else {
            return OfyService.ofy().load().type(Match.class).id(id).now();
        }
    }

    @Override
    public void deleteMatch(Long id) {
        OfyService.ofy().delete().type(Match.class).id(id).now();
        clearCache();
    }

    @Override
    public List<Match> getAll() {
        List<Match> cachedValue = getMatchsFromCache();

        //checking if the value exists in the cache
        if (cachedValue != null) {
            logger.log(Level.FINE, "getAll() returns cached value");
            return cachedValue;
        } else {
            //if not, matchs are fetched in the database and added to the cache
            List<Match> matchs = OfyService.ofy().load().type(Match.class).list();
            if (cache != null) {
                cache.put(MATCH_LIST_CACHE_KEY, Lists.newArrayList(matchs));
            }
            return matchs;
        }
    }

    @Override
    public void deleteAll() {
        List<Key<Match>> keys = OfyService.ofy().load().type(Match.class).keys().list();
        OfyService.ofy().delete().keys(keys).now();
        clearCache();
    }


}
