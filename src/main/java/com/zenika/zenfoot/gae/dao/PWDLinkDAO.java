package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.zenika.zenfoot.gae.utils.PWDLink;

import java.util.List;

/**
 * Created by raphael on 18/08/14.
 */
public class PWDLinkDAO {

    public Key<PWDLink> save(PWDLink pwdLink){
        return ObjectifyService.ofy().save().entity(pwdLink).now();
    }

    public PWDLink get(Long pwdLinkId) {
        return ObjectifyService.ofy().load().type(PWDLink.class).id(pwdLinkId).now();
    }

    public void delete(Long pwdLinkId){
       ObjectifyService.ofy().delete().type(PWDLink.class).id(pwdLinkId).now();
    }

    public List<PWDLink> getAll(){
        return ObjectifyService.ofy().load().type(PWDLink.class).list();
    }
}
